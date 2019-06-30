package com.sc.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sc.api.constant.IConstants;
import com.sc.api.model.Pd;
import com.sc.api.response.ReturnModel;
import com.sc.api.util.DateUtil;
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
		mv.addObject("pda", pda);

		if (null != pda) {
			Pd pdm = new Pd();
			pdm.put("MODALITIES_ID", pda.getString("MODALITIES_ID"));
			pdm = rest.post(IConstants.SC_SERVICE_KEY, "modalities/find", pdm, Pd.class);
			mv.setViewName("activities/" + pdm.getString("PAGE"));

			Pd pdap = new Pd();
			pdap.put("ACTIVITIES_ID", pda.getString("ACTIVITIES_ID"));
			List<Pd> activitiesprizeitemsData = rest.postForList(IConstants.SC_SERVICE_KEY,
					"activities/listAllPrizeitems", pdap, new ParameterizedTypeReference<List<Pd>>() {
					});
			mv.addObject("activitiesprizeitemsData", activitiesprizeitemsData);

		} else {
			mv.setViewName("activities/index");
		}

		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = "activities/createDraw")
	@ResponseBody
	public ReturnModel createDraw() throws Exception {
		ReturnModel rm = new ReturnModel();
		Pd pd = new Pd();
		pd = this.getPd();
		pd.put("PRIZEITEMS_ID", "7");
		pd.put("CREATE_TIME", DateUtil.getTime());
		pd.put("STATE", IConstants.STRING_0);
		rest.post(IConstants.SC_SERVICE_KEY, "drawuser/save", pd, Pd.class);
		pd.put("PRIZEITEMS_INDEX", 0);
		rm.setData(pd);
		return rm;
	}

	@RequestMapping(value = "activities/listDraw")
	public ModelAndView listDraw() throws Exception {
		logger.info("进入查看抽奖记录");
		ModelAndView mv = new ModelAndView();
		Pd pd = new Pd();
		pd = this.getPd();
		List<Pd> drawuserData = rest.postForList(IConstants.SC_SERVICE_KEY, "drawuser/listAll", pd,
				new ParameterizedTypeReference<List<Pd>>() {
				});
		mv.addObject("drawuserData", drawuserData);

		Pd pda = new Pd();
		pda.put("ACTIVITIES_ID", pd.getString("ACTIVITIES_ID"));
		pda = rest.post(IConstants.SC_SERVICE_KEY, "activities/find", pda, Pd.class);
		mv.addObject("pda", pda);

		Pd pdar = new Pd();
		pdar.put("MEMBER_ID", pd.getString("MEMBER_ID"));
		pdar = rest.post(IConstants.SC_SERVICE_KEY, "member/findAddress", pdar, Pd.class);
		if (null == pdar) {
			pdar = new Pd();
			pdar.put("MEMBER_ID", pd.getString("MEMBER_ID"));
			pdar = rest.post(IConstants.SC_SERVICE_KEY, "member/saveAddress", pdar, Pd.class);
		}
		mv.addObject("pdar", pdar);

		mv.setViewName("activities/draw");
		return mv;
	}

	@RequestMapping(value = "activities/manageAddress")
	@ResponseBody
	public ReturnModel manageAddress() throws Exception {
		ReturnModel rm = new ReturnModel();
		Pd pd = new Pd();
		pd = this.getPd();
		pd = rest.post(IConstants.SC_SERVICE_KEY, "member/editAddress", pd, Pd.class);
		rm.setData(pd);
		return rm;
	}
}
