package com.sc.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sc.api.constant.IConstants;
import com.sc.api.model.Pd;
import com.sc.app.util.RestTemplateUtil;

@Controller
public class ActivitiesController extends BaseController {

	@Autowired
	RestTemplateUtil rest;

	@RequestMapping(value = { "/activities" })
	public ModelAndView activities() {
		logger.info("进入营销");
		ModelAndView mv = new ModelAndView();
		Pd pd = new Pd();
		pd = this.getPd();

		Pd pda = new Pd();
		pda.put("COMPANY_ID", pd.getString("COMPANY_ID"));
		pda.put("GOODS_ID", pd.getString("GOODS_ID"));
		pda.put("BATCH_ID", pd.getString("BATCH_ID"));
		pda.put("STATE", IConstants.STRING_1);
		pda = rest.post(IConstants.SC_SERVICE_KEY, "activities/findBy", pda, Pd.class);

		if (null != pda) {
			Pd pdm = new Pd();
			pdm.put("MODALITIES_ID", pda.getString("MODALITIES_ID"));
			pdm = rest.post(IConstants.SC_SERVICE_KEY, "modalities/find", pdm, Pd.class);
			mv.setViewName("activities/" + pdm.getString("PAGE"));
		} else {
			mv.setViewName("activities/index");
		}

		mv.addObject("pd", pd);
		return mv;
	}
}
