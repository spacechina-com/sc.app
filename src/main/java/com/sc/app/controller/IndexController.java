package com.sc.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(value = { "/welcome", "/" })
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("init/init");
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
		//user.put("NICKNAME", userJO.getString("nickname"));
		//user.put("SEX", userJO.getString("sex"));
		//user.put("PHOTO", userJO.getString("headimgurl"));
		user.put("CDT", DateUtil.getTime());

		if (null == person) {
			person = rest.post(IConstants.SC_SERVICE_KEY, "member/save", user, Pd.class);
		} else {
			//person.put("NICKNAME", userJO.getString("nickname"));
			//person.put("SEX", userJO.getString("sex"));
			//person.put("PHOTO", userJO.getString("headimgurl"));
			rest.post(IConstants.SC_SERVICE_KEY, "member/edit", person, Pd.class);
		}
		getSession().setAttribute(IConstants.USER_SESSION, person);
		mv.setViewName("redirect:/home");
		return mv;
	}

}
