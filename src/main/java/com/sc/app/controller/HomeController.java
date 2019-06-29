package com.sc.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sc.api.model.Pd;
import com.sc.app.util.RestTemplateUtil;

@Controller
public class HomeController extends BaseController {

	@Autowired
	RestTemplateUtil rest;

	@RequestMapping(value = { "/home" })
	public ModelAndView index() {
		logger.info("进入首页");
		ModelAndView mv = new ModelAndView();
		Pd pd = new Pd();
		pd = this.getPd();
		mv.setViewName("home/index");
		mv.addObject("pd", pd);
		return mv;
	}
}
