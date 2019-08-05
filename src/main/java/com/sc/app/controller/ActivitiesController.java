package com.sc.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${server.hostname.master}")
	private String HOSTNAMEMASTER;

	@Autowired
	RestTemplateUtil rest;

	@RequestMapping(value = { "/activities" })
	public ModelAndView activities() {
		logger.info("进入营销");
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

		Pd pda = new Pd();
		pda.putAll(pdc);
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

			mv.addObject("HOSTNAMEMASTER", HOSTNAMEMASTER);

		} else {
			logger.info("无关联活动");
			mv.setViewName("temp/nodata");
		}

		mv.addObject("snid", snid);
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = "activities/createDraw")
	@ResponseBody
	public ReturnModel createDraw() throws Exception {
		ReturnModel rm = new ReturnModel();
		Pd pd = new Pd();
		pd = this.getPd();

		Pd pda = new Pd();
		pda.put("ACTIVITIES_ID", pd.getString("ACTIVITIES_ID"));
		pda = rest.post(IConstants.SC_SERVICE_KEY, "activities/find", pda, Pd.class);

		if (StringUtils.isNotEmpty(pda.getString("PEOPLE_LIMIT"))) {
			Pd pds = new Pd();
			pds.put("ACTIVITIES_ID", pd.getString("ACTIVITIES_ID"));
			pds.put("SNID", pd.getString("SNID"));
			List<Pd> allData = rest.postForList(IConstants.SC_SERVICE_KEY, "activities/listAllCodes", pds,
					new ParameterizedTypeReference<List<Pd>>() {
					});
			pds.put("MEMBER_ID", pd.getString("MEMBER_ID"));
			List<Pd> meData = rest.postForList(IConstants.SC_SERVICE_KEY, "activities/listAllCodes", pds,
					new ParameterizedTypeReference<List<Pd>>() {
					});
			if (allData.size() >= Integer.parseInt(pda.getString("PEOPLE_LIMIT")) && meData.size() == 0) {
				rm.setFlag(false);
				rm.setMessage("单码可用人数已达到上线,建议购买产品扫描新码参与活动");
				return rm;
			} else {
				if (meData.size() == 0) {
					pds.put("CREATE_TIME", DateUtil.getTime());
					rest.post(IConstants.SC_SERVICE_KEY, "activities/saveCodes", pds, Pd.class);
				}
			}
		}

		String day_unused = null;

		if (StringUtils.isNotEmpty(pda.getString("SINGLE_LIMIT"))) {
			Pd pds = new Pd();
			pds.put("ACTIVITIES_ID", pd.getString("ACTIVITIES_ID"));
			pds.put("MEMBER_ID", pd.getString("MEMBER_ID"));
			pds.put("SNID", pd.getString("SNID"));
			List<Pd> drawuserData = rest.postForList(IConstants.SC_SERVICE_KEY, "drawuser/listAll", pds,
					new ParameterizedTypeReference<List<Pd>>() {
					});
			if (drawuserData.size() >= Integer.parseInt(pda.getString("SINGLE_LIMIT"))) {
				rm.setFlag(false);
				rm.setMessage("单人抽奖次数已达到上线");
				return rm;
			} else {
				day_unused = "单人剩余" + (Integer.parseInt(pda.getString("SINGLE_LIMIT")) - drawuserData.size() - 1)
						+ "次抽奖机会";
			}
		}

		if (StringUtils.isNotEmpty(pda.getString("DAY_LIMIT"))) {
			Pd pdd = new Pd();
			pdd.put("ACTIVITIES_ID", pd.getString("ACTIVITIES_ID"));
			pdd.put("MEMBER_ID", pd.getString("MEMBER_ID"));
			pdd.put("SNID", pd.getString("SNID"));
			pdd.put("DAYSTR", DateUtil.getDay());
			List<Pd> drawuserData = rest.postForList(IConstants.SC_SERVICE_KEY, "drawuser/listAll", pdd,
					new ParameterizedTypeReference<List<Pd>>() {
					});
			if (drawuserData.size() >= Integer.parseInt(pda.getString("DAY_LIMIT"))) {
				rm.setFlag(false);
				rm.setMessage("单人日抽奖次数已达到上线");
				return rm;
			} else {
				day_unused = "今日剩余" + (Integer.parseInt(pda.getString("DAY_LIMIT")) - drawuserData.size() - 1)
						+ "次抽奖机会";
			}
		}

		String[] rate = createRate(pda, 1000);

		String AUTOHANDER = IConstants.STRING_0;
		if (IConstants.STRING_1.equals(rate[3])) {
			AUTOHANDER = IConstants.STRING_1;
		}

		pd.put("PRIZEITEMS_ID", rate[0]);
		pd.put("CREATE_TIME", DateUtil.getTime());
		pd.put("STATE", AUTOHANDER);
		rest.post(IConstants.SC_SERVICE_KEY, "drawuser/save", pd, Pd.class);
		pd.put("PRIZEITEMS_INDEX", rate[1]);
		pd.put("DESCRIPTION", rate[2]);
		pd.put("DAY_UNUSED", day_unused);
		rm.setData(pd);
		return rm;
	}

	@RequestMapping(value = "activities/listDraw")
	public ModelAndView listDraw() throws Exception {
		logger.info("进入查看抽奖记录");
		ModelAndView mv = new ModelAndView();
		Pd pd = new Pd();
		pd = this.getPd();

		String snid = pd.getString("snid");

		pd.put("SNID", snid);
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

		mv.addObject("snid", snid);

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

	private String[] createRate(Pd pd, int limit) {
		String[] result = new String[4];
		Pd pdap = new Pd();
		pdap.put("ACTIVITIES_ID", pd.getString("ACTIVITIES_ID"));
		List<Pd> activitiesprizeitemsData = rest.postForList(IConstants.SC_SERVICE_KEY, "activities/listAllPrizeitems",
				pdap, new ParameterizedTypeReference<List<Pd>>() {
				});
		List<String> temp = new ArrayList<String>();
		for (Pd one : activitiesprizeitemsData) {
			int p = Integer.parseInt(one.getString("PERCENT"));
			for (int i = 0; i < p; i++) {
				temp.add(one.getString("ACTIVITIES_PRIZEITEMS_ID"));
			}
		}

		if (temp.size() >= limit) {
			temp = temp.subList(0, limit);
		} else {

		}

		Collections.shuffle(temp);

		int index = new Random().nextInt(temp.size());
		String prizeitem_id = temp.get(index);

		for (int i = 0; i < activitiesprizeitemsData.size(); i++) {
			if (activitiesprizeitemsData.get(i).getString("ACTIVITIES_PRIZEITEMS_ID").equals(prizeitem_id)) {
				result[0] = activitiesprizeitemsData.get(i).getString("PRIZEITEMS_ID");
				result[1] = i + "";
				result[2] = activitiesprizeitemsData.get(i).getString("DESCRIPTION");
				result[3] = activitiesprizeitemsData.get(i).getString("AUTOHANDER");
				break;
			}
		}
		return result;
	}
}
