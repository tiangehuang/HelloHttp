package com.epro.pms.project_basic_info.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epro.pms.exception.CommonException;
import com.epro.pms.project_basic_info.service.WeeklyloadService;
import com.epro.pms.util.po.Header;
import com.epro.pms.util.po.Response;
import com.epro.pms.util.po.RspConst;
@Controller
@RequestMapping("/weeklyload")
public class WeeklyloadController
{
	private static Log log = LogFactory.getLog(WeeklyloadController.class);
	
	@Autowired
	private WeeklyloadService service;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Map<String, Object> addWeeklyload(@RequestBody Map<String, Object> paramMap) throws Exception
	{
		log.info("invoking..." + paramMap);
		Map<String, Object> header = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> request = (Map<String, Object>) paramMap.get("request");
		
		service.insert(request);
		
		return null;
	}
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/savePeriod", method = RequestMethod.POST)
	public Map<String, Object> savePeriod(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("Controller: WeeklyloadController.savePeriod() is invoked. param=" + paramMap);
		Map<String, Object> header = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> request = (Map<String, Object>) paramMap.get("request");
		
		// 参数验证
		try
		{
			Map<String, Object> weeklyload = (Map<String, Object>) request.get("weeklyload");
			List<Map<String, Object>> emps = (List<Map<String, Object>>) request.get("emps");
		} catch (Exception e)
		{
			log.info("Controller: illegal parameter. message>>>" + e.getMessage());

            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put(RspConst.HEADER, new Header(RspConst.ARGS_ERROR_CODE, RspConst.ARGS_ERROR_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, new Response("result", RspConst.ARGS_ERROR_MSG));

            log.info("Controller: WeeklyloadController.savePeriod() is untimely returned, result=" + resMap);
            return resMap;
		}
		// 请求服务层
		service.savePeriod(request);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", null));
		
		return resMap;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/addPeriod", method = RequestMethod.POST)
	public Map<String, Object> addPeriod(@RequestBody Map<String, Object> paramMap) throws Exception
	{
		log.info("Controller: WeeklyloadController.addPeriod() is invoked. param=" + paramMap);
		Map<String, Object> header = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> request = (Map<String, Object>) paramMap.get("request");
		
		try
		{
			Object tmp = request.get("projectId");
		} catch (Exception e)
		{
			log.info("Controller: illegal parameter. message>>>" + e.getMessage());

            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put(RspConst.HEADER, new Header(RspConst.ARGS_ERROR_CODE, RspConst.ARGS_ERROR_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, new Response("result", RspConst.ARGS_ERROR_MSG));

            log.info("Controller: WeeklyloadController.addPeriod() is untimely returned, result=" + resMap);
            return resMap;
		}
		
		service.addPeriod(request);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", null));
		
		return resMap;
	}
	/**
	 * @Desc 周报的首页调用方法
	 * @param paramMap
	 * @return
	 * @throws CommonException
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getProjectsWeeklyloads", method = RequestMethod.POST)
	public Map<String, Object> getProjectsWeeklyloads(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		Map<String, Object> header = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> request = (Map<String, Object>) paramMap.get("request");
		
		List<Map<String, Object>> weeklyloadReport = service.getProjectsWeeklyloads(request);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", weeklyloadReport));
		
		return resMap;
	}
	/**
	 * @Desc 通过周期获取周报
	 * @param paramMap
	 * @return
	 * @throws CommonException
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getProjectsWeeklyload", method = RequestMethod.POST)
	public Map<String, Object> getProjectsWeeklyload(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		Map<String, Object> header = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> request = (Map<String, Object>) paramMap.get("request");
		
		Map<String, Object> weeklyloadReport = service.getProjectWeeklyload(request);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", weeklyloadReport));
		
		return resMap;
	}

}










