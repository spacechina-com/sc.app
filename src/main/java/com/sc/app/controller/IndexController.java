package com.sc.app.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sc.api.constant.IConstants;
import com.sc.api.model.Pd;
import com.sc.api.util.DateUtil;
import com.sc.app.util.RestTemplateUtil;
import com.sc.app.util.WXUtil;

import net.sf.json.JSONObject;

@Controller
public class IndexController extends BaseController {

	@Autowired
	RestTemplateUtil rest;

	@Value("${server.hostname}")
	private String HOSTNAME;

	@RequestMapping(value = { "/welcome", "/" })
	public ModelAndView index() throws Exception {
		ModelAndView mv = new ModelAndView();
		Pd pd = new Pd();
		pd = this.getPd();

		pd.put("COMPANY_ID", pd.getString("company_id"));

		pd = rest.post(IConstants.SC_SERVICE_KEY, "merchant/findBy", pd, Pd.class);

		if (null == pd || StringUtils.isEmpty(pd.getString("APPID"))
				|| StringUtils.isEmpty(pd.getString("APPSECRET"))) {
			mv.setViewName("init/warn");
		} else {
			mv.addObject("APPID", pd.getString("APPID"));
			mv.addObject("RETURN", URLEncoder.encode(HOSTNAME + "/scapp/init?company_id=" + pd.getString("company_id")
					+ "&goods_id=" + pd.getString("goods_id") + "&batch_id=" + pd.getString("batch_id"), "UTF-8"));
			mv.setViewName("init/init");
		}

		return mv;
	}

	@RequestMapping("/init")
	public ModelAndView init(HttpServletRequest request) throws Exception {
		logger.info("=============进入初始化=============");
		ModelAndView mv = new ModelAndView();

		String code = request.getParameter("code");
		logger.info("wxcode=" + code);
		if (StringUtils.isEmpty(code)) {
			logger.info("wxcode为空重定向初始化");
			mv.setViewName("redirect:/");
			return mv;
		}
		String openID = WXUtil.openId(code);
		logger.info("openid=" + openID);
		if (StringUtils.isEmpty(openID)) {
			logger.info("openid为空重定向初始化");
			mv.setViewName("redirect:/");
			return mv;
		}

		Pd person = new Pd();
		person.put("OPENID", openID);
		person = rest.post(IConstants.SC_SERVICE_KEY, "member/findBy", person, Pd.class);

		JSONObject userJO = WXUtil.user(openID);
		logger.info("微信用户信息:" + userJO.toString());
		Pd user = new Pd();
		user.put("OPENID", userJO.getString("openid"));
		// user.put("NICKNAME", userJO.getString("nickname"));
		// user.put("SEX", userJO.getString("sex"));
		// user.put("PHOTO", userJO.getString("headimgurl"));
		user.put("CDT", DateUtil.getTime());

		if (null == person) {
			person = rest.post(IConstants.SC_SERVICE_KEY, "member/save", user, Pd.class);
		} else {
			// person.put("NICKNAME", userJO.getString("nickname"));
			// person.put("SEX", userJO.getString("sex"));
			// person.put("PHOTO", userJO.getString("headimgurl"));
			rest.post(IConstants.SC_SERVICE_KEY, "member/edit", person, Pd.class);
		}
		getSession().setAttribute(IConstants.USER_SESSION, person);
		mv.setViewName("redirect:/home");
		return mv;
	}

}
