/**
 * 
 */
package com.epro.pms.project_basic_info.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;





import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epro.pms.exception.CommonException;
import com.epro.pms.project_basic_info.po.StatisticalFormInfo;
import com.epro.pms.project_basic_info.service.StatisticalFormService;
import com.epro.pms.util.JSONUtils;
import com.epro.pms.util.po.Header;
import com.epro.pms.util.po.Response;
import com.epro.pms.util.po.RspConst;

/**
 * @author epro
 *
 */
@Controller
@RequestMapping("/statisticalForm")
public class StatisticalFormController {
	
	@Autowired
	private StatisticalFormService statsFormService;
	private Logger logger=LogManager.getLogger(StatisticalFormController.class);
	
	
	@ResponseBody
	@RequestMapping(value="/list.do",method = RequestMethod.POST)
	public Map<String,Object> listStatisticalForm(@RequestBody Map<String, Object> paramMap) throws CommonException{
		logger.info("--22---进入StatisticalFormController类listStatisticalForm方法-------");
		// step1.获取参数，验证参数
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		// step2. 调用service层，获取结果
		StatisticalFormInfo info = new StatisticalFormInfo();
		info.setArea(requestMap.get("area").toString());
		info.setProductLine(requestMap.get("productLine").toString());
		info.setePmName(requestMap.get("ePmName").toString());
		
		List<StatisticalFormInfo> listStatisticalForm = statsFormService.listStatisticalForm(info);
		
		// step3. 封装返回结果
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result",listStatisticalForm));
		return resMap;

	}
	
}
