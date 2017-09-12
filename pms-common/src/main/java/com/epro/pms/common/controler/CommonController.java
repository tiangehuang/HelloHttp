package com.epro.pms.common.controler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epro.pms.common.po.OptionPO;
import com.epro.pms.common.service.ICommonService;
import com.epro.pms.util.po.Header;
import com.epro.pms.util.po.Response;
import com.epro.pms.util.po.RspConst;

@RequestMapping("/common")
@Controller
public class CommonController {
	
	private static Log log = LogFactory.getLog(CommonController.class);
	
	@Autowired
	private ICommonService commonServiceImpl;
	
	@ResponseBody
	@RequestMapping("/queryOptionList")
	public Map<String, Object> queryOptionList(@RequestBody Map<String, Object> paramMap) {
		log.info("请求下拉框数据，参数=" + paramMap);
		@SuppressWarnings("unchecked")
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get(RspConst.REQUEST);
		
		List<OptionPO> optionList = commonServiceImpl.queryOptionList(requestMap);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,0,optionList.size(),optionList.size()));
		resMap.put(RspConst.RESPONSE, new Response("optionList",optionList));
		
		log.info("返回下拉框数据，result=" + resMap);
		return resMap;
	}
	

	@ResponseBody
	@RequestMapping("/queryProLineOptionList")
	public Map<String, Object> queryProLineOptionList(@RequestBody Map<String, Object> paramMap) {
		log.info("请求产品线下拉框数据，参数=" + paramMap);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get(RspConst.REQUEST);
		
		List<OptionPO> optionList = commonServiceImpl.queryProLineOptionList(requestMap);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,0,optionList.size(),optionList.size()));
		resMap.put(RspConst.RESPONSE, new Response("optionList",optionList));
		
		log.info("返回产品线下拉框数据，result=" + resMap);
		return resMap;
	}
	
	@ResponseBody
	@RequestMapping("/queryPDUOptionList")
	public Map<String, Object> queryPDUOptionList(@RequestBody Map<String, Object> paramMap) {
		log.info("请求pdu下拉框数据，参数=" + paramMap);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get(RspConst.REQUEST);
		
		List<OptionPO> optionList = commonServiceImpl.queryPDUOptionList(requestMap);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,0,optionList.size(),optionList.size()));
		resMap.put(RspConst.RESPONSE, new Response("optionList",optionList));
		
		log.info("返回pdu下拉框数据，result=" + resMap);
		return resMap;
	}
	
	
	@ResponseBody
	@RequestMapping("/saveNewProLineAndPdu")
	public Map<String, Object> saveNewProLineAndPdu(@RequestBody Map<String, Object> paramMap) {
		log.info("新增新的产品线和产品，参数=" + paramMap);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get(RspConst.REQUEST);
		
		String productLine = (String) requestMap.get("productLine");
		String pdu = (String) requestMap.get("pdu");
		
		if (StringUtils.isEmpty(productLine) || StringUtils.isEmpty(pdu)) {
			log.info("------存在空数据，舍弃操作------");
		} else {
			commonServiceImpl.saveNewProLineAndPdu(requestMap);
		}
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,0,0,0));
		resMap.put(RspConst.RESPONSE, null);
		
		log.info("新增新的产品线和产品，result=" + resMap);
		return resMap;
	}
	
	@ResponseBody
	@RequestMapping("/generateProjTeamNo")
	public Map<String, Object> generateProjTeamNo(@RequestBody Map<String, Object> paramMap) {
		@SuppressWarnings("unchecked")
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get(RspConst.REQUEST);
		
		String projTeamNo = commonServiceImpl.generateProjTeamNo(requestMap);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,0,0,0));
		resMap.put(RspConst.RESPONSE, new Response("projTeamNo" , projTeamNo));
		
		return resMap;
	}
}
