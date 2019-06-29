package com.sc.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController extends BaseController {

	@RequestMapping(value = { "/init", "/" })
	public ModelAndView index() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("init/init");
		return mv;
	}

}
