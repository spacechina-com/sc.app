package com.sc.app.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.sc.api.constant.IConstants;
import com.sc.api.model.Page;
import com.sc.api.model.Pd;

@Component
public class BaseController {

	protected Logger logger = LoggerFactory.getLogger(BaseController.class);

	protected String SYSTEM_NAME;

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	protected HttpSession session;

	@Autowired
	private MessageSource messageSource;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {

		this.request = request;

		this.response = response;

		this.session = request.getSession();

	}

	/**
	 * new Pd对象
	 * 
	 * @return
	 */
	public Pd getPd() {
		return new Pd(this.getRequest());
	}

	/**
	 * 得到ModelAndView
	 * 
	 * @return
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpSession getSession() {
		return session;
	}

	/**
	 * 得到32位的uuid
	 * 
	 * @return
	 */
	public String get32UUID() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}

	/**
	 * 得到分页列表的信息
	 * 
	 * @return
	 */
	public Page getPage() {
		HttpServletRequest request = getRequest();
		Page page = new Page();
		String currentPage = request.getParameter("page_currentPage");
		if (StringUtils.isNotEmpty(currentPage)) {
			page.setCurrentPage(Integer.parseInt(currentPage));
		} else {
			return page;
		}
		String showCount = request.getParameter("page_showCount");
		if (StringUtils.isNotEmpty(showCount)) {
			page.setShowCount(Integer.parseInt(showCount));
		}
		return page;
	}

	public String getIP() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		if (ip == null) {
			ip = "0.0.0.0";
		}
		if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
		if (ip.contains(",")) {
			ip = ip.split(",")[0];
		}
		return ip;
	}

	public String getMessage(String key, Object[] objects, String defaultStr) {

		return messageSource.getMessage(key, objects, Locale.CHINESE);
	}

	public String getMessageUrl(String code, Object[] args, String defaultMessage) {
		String msg = messageSource.getMessage(code, args, defaultMessage, Locale.CHINESE);
		try {
			msg = URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return msg;
	}

	public Pd userSession() {
		return (Pd) getSession().getAttribute(IConstants.USER_SESSION);
	}

	public String memberId() {
		return userSession().getString("MEMBER_ID");
	}

	public String openId() {
		return userSession().getString("WXOPEN_ID");
	}

}
