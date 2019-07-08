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

	@RequestMapping(value = { "/welcome" })
	public ModelAndView index() throws Exception {
		logger.info("=============进入系统=============");
		ModelAndView mv = new ModelAndView();
		Pd pd = new Pd();
		pd = this.getPd();

		String snid = pd.getString("snid");

		Pd pdc = new Pd();
		pdc.put("CODE", snid);
		pdc = rest.post(IConstants.SC_SERVICE_KEY, "common/info/find", pdc, Pd.class);

		if (null == pdc) {
			logger.info("参数异常");
			mv.setViewName("temp/param");
			return mv;
		}

		Pd pdm = new Pd();
		pdm.put("COMPANY_ID", pdc.getString("COMPANY_ID"));
		pdm = rest.post(IConstants.SC_SERVICE_KEY, "merchant/findBy", pdm, Pd.class);

		if (null == pdm || StringUtils.isEmpty(pdm.getString("APPID"))
				|| StringUtils.isEmpty(pdm.getString("APPSECRET"))) {
			logger.info("公众号配置缺失");
			mv.setViewName("temp/warn");
		} else {
			mv.addObject("APPID", pdm.getString("APPID"));
			mv.addObject("RETURN", URLEncoder.encode(HOSTNAME + "/scapp/init?snid=" + snid, "UTF-8"));
			mv.setViewName("init/init");
		}

		return mv;
	}

	@RequestMapping("/init")
	public ModelAndView init(HttpServletRequest request) throws Exception {
		logger.info("=============进入初始化=============");
		ModelAndView mv = new ModelAndView();
		Pd pd = new Pd();
		pd = this.getPd();

		String snid = pd.getString("snid");

		Pd pdc = new Pd();
		pdc.put("CODE", snid);
		pdc = rest.post(IConstants.SC_SERVICE_KEY, "common/info/find", pdc, Pd.class);

		if (null == pdc) {
			logger.info("参数异常");
			mv.setViewName("temp/param");
			return mv;
		}

		Pd pdm = new Pd();
		pdm.put("COMPANY_ID", pdc.getString("COMPANY_ID"));
		pdm = rest.post(IConstants.SC_SERVICE_KEY, "merchant/findBy", pdm, Pd.class);

		String APPID = pdm.getString("APPID");
		String APPSECRET = pdm.getString("APPSECRET");

		if (StringUtils.isEmpty(APPID) || StringUtils.isEmpty(APPSECRET)) {
			logger.info("公众号配置缺失");
			mv.setViewName("temp/warn");
			return mv;
		}

		String code = request.getParameter("code");
		logger.info("wxcode=" + code);
		if (StringUtils.isEmpty(code)) {
			logger.info("wxcode为空重定向初始化");
			mv.setViewName("redirect:/welcome?snid=" + snid);
			return mv;
		}
		String openID = new WXUtil(APPID, APPSECRET).openId(code);
		logger.info("openid=" + openID);
		if (StringUtils.isEmpty(openID)) {
			logger.info("openid为空重定向初始化");
			mv.setViewName("redirect:/welcome?snid=" + snid);
			return mv;
		}

		Pd person = new Pd();
		person.put("OPENID", openID);
		person = rest.post(IConstants.SC_SERVICE_KEY, "member/findBy", person, Pd.class);

		JSONObject userJO = new WXUtil(APPID, APPSECRET).user(openID);
		logger.info("微信用户信息:" + userJO.toString());
		Pd user = new Pd();
		user.put("OPENID", userJO.getString("openid"));

		try {
			user.put("NICKNAME", userJO.getString("nickname"));
			user.put("SEX", userJO.getString("sex"));
			user.put("PHOTO", userJO.getString("headimgurl"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.put("CDT", DateUtil.getTime());

		if (null == person) {
			user.put("COMPANY_ID", pd.get("company_id"));
			person = rest.post(IConstants.SC_SERVICE_KEY, "member/save", user, Pd.class);
		} else {
			try {
				person.put("NICKNAME", userJO.getString("nickname"));
				person.put("SEX", userJO.getString("sex"));
				person.put("PHOTO", userJO.getString("headimgurl"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			rest.post(IConstants.SC_SERVICE_KEY, "member/edit", person, Pd.class);
		}
		getSession().setAttribute(IConstants.USER_SESSION, person);
		mv.setViewName("redirect:/activities?snid=" + snid);
		return mv;
	}

}
