package com.epro.pms.project_basic_info.controller;

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

import com.epro.pms.exception.IllegalParameterException;
import com.epro.pms.project_basic_info.service.IProjEmployeeService;
import com.epro.pms.util.ParamUtils;
import com.epro.pms.util.po.Header;
import com.epro.pms.util.po.Response;
import com.epro.pms.util.po.RspConst;

/**
 * 
 * @author epro max
 *
 */
@Controller
@RequestMapping("/project")
public class ProjEmployeeController {
	private static Log log = LogFactory.getLog(ProjEmployeeController.class);
	
	@Autowired
	private IProjEmployeeService projEmployeeService;
	
	@RequestMapping("/deleteEmployeeFromProject")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, Object> deleteEmployeeFromProject(@RequestBody Map<String,Object> paramMap) {
		log.info("Controller: ProjEmployeeController.deleteEmployeeFromProject() is invoked. param=" + paramMap);
		// step1. 验证参数
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Map<String, Object> employee = (Map<String, Object>) requestMap.get("employee");
		
		try
        {
            if (employee.isEmpty()) {
            	throw new IllegalParameterException("员工列表为空！");
            }
        }
        catch (IllegalParameterException e)
        {
            log.info("Controller: illegal parameter. message>>>" + e.getMessage());

            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put(RspConst.HEADER, new Header(RspConst.ARGS_ERROR_CODE, RspConst.ARGS_ERROR_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, new Response("result", RspConst.ARGS_ERROR_MSG));

            log.info("Controller: ProjEmployeeController.addEmployeesToProject() is untimely returned, result=" + resMap);
            return resMap;
        }
		
		// step2. 请求服务层
		boolean flag = projEmployeeService.deleteEmployeeFromProject(requestMap);
		
		// step3. 封装返回
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		if (flag) {
            resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE, RspConst.SUCCESS_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, new Response("msg", RspConst.SUCCESS_MSG));
            log.info("Controller: ProjEmployeeController.deleteEmployeeFromProject() is successfully returned, result=" + resMap);
		} else {
			resMap.put(RspConst.HEADER, new Header(RspConst.API_FAIL_CODE, RspConst.API_FAIL_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, null);
			log.info("Controller: the response of remote api is not normally returned, result=" + resMap);
		}
		return resMap;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryEmployeeListByKeyword")
	@ResponseBody
	public Map<String, Object> queryEmployeeListByKeyword(@RequestBody Map<String,Object> paramMap) {
		log.info("Controller: ProjEmployeeController.queryEmployeeListByKeyword() is invoked. param=" + paramMap);
		// step1. 验证参数
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		try
        {
			int beginnum = (int) headerMap.get("beginnum");		// 起始下标
			int endnum = (int) headerMap.get("endnum");			// 终止下标
            String keyword = (String) requestMap.get("keyword"); // 关键字
            // 验证keyword,验证类别EMPTY验证
            ParamUtils.validateParameter(queryMap, "keyword", keyword, ParamUtils.EMPTY);
        
            if (beginnum >= 0 && endnum > beginnum) {
            	queryMap.put("beginnum", beginnum);
            	queryMap.put("endnum", endnum);
            } else {
            	throw new IllegalParameterException("分页参数有误. beginnum=" + beginnum + ",endnum=" + endnum);
            }
        }
        catch (IllegalParameterException e)
        {
            log.info("Controller: illegal parameter. message>>>" + e.getMessage());

            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put(RspConst.HEADER, new Header(RspConst.ARGS_ERROR_CODE, RspConst.ARGS_ERROR_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, new Response("result", RspConst.ARGS_ERROR_MSG));

            log.info("Controller: ProjEmployeeController.queryEmployeeListByKeyword() is untimely returned, result=" + resMap);
            return resMap;
        }
		
		// step2. 请求服务层
		Map<String, Object> result = projEmployeeService.queryEmployeeListByKeyword(queryMap);
		
		// step3. 封装返回
		Map<String, Object> resMap = new HashMap<String, Object>();
		String rspcode =  (String) result.get("rspcode");
		if ("0000".equals(rspcode)) {
            resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE, RspConst.SUCCESS_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, result.get("response"));
            log.info("Controller: ProjEmployeeController.queryEmployeeListByKeyword() is successfully returned, result=" + resMap);
		} else {
			resMap.put(RspConst.HEADER, new Header(RspConst.API_FAIL_CODE, RspConst.API_FAIL_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, null);
			log.info("Controller: the response of remote api is not normally returned, result=" + resMap);
		}
		return resMap;
	}
	
	@RequestMapping("/queryEmployeeByNo")
	@ResponseBody
	public Map<String, Object> queryEmployeeByNo(@RequestBody Map<String,Object> paramMap) {
		return null;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryEmployeeListByConditions")
	@ResponseBody
	public Map<String, Object> queryEmployeeListByConditions(@RequestBody Map<String,Object> paramMap) {
        
		// step1. 验证参数
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		
		try
        {
            int beginnum = (int) headerMap.get("beginnum"); 	// 起始下标
            int endnum = (int) headerMap.get("endnum"); 		// 结束小标
            String belongcompany = (String) requestMap.get("belongcompany"); 	// 
            String projectteam = (String) requestMap.get("projectteam"); 	// 
            String productline = (String) requestMap.get("productline"); 	// 
            
            // 验证分页参数
            if (beginnum >= 0 && endnum > beginnum) {
            	queryMap.put("beginnum", beginnum);
            	queryMap.put("endnum", endnum);
            } else {
            	throw new IllegalParameterException("分页参数有误. beginnum=" + beginnum + ",endnum=" + endnum);
            }
            
            // 验证belongcompany,验证类别EMPTY验证
            ParamUtils.validateParameter(queryMap, "belongcompany", belongcompany, ParamUtils.NULL);
            // 验证projectteam,验证类别EMPTY验证
            ParamUtils.validateParameter(queryMap, "projectteam", projectteam, ParamUtils.NULL);
            // 验证productline,验证类别EMPTY验证
            ParamUtils.validateParameter(queryMap, "productline", productline, ParamUtils.NULL);
        }
        catch (IllegalParameterException e)
        {
            log.info("Controller: illegal parameter. message>>>" + e.getMessage());

            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put(RspConst.HEADER, new Header(RspConst.ARGS_ERROR_CODE, RspConst.ARGS_ERROR_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, new Response("result", RspConst.ARGS_ERROR_MSG));

            log.info("Controller: ProjEmployeeController.queryEmployeeListByConditions() is untimely returned, result=" + resMap);
            return resMap;
        }
		
		// step2. 请求服务层
		Map<String, Object> result = projEmployeeService.queryEmployeeListByConditions(queryMap);
		
		// step3. 封装返回
		Map<String, Object> resMap = new HashMap<String, Object>();
		String rspcode =  (String) result.get("rspcode");
		if ("0000".equals(rspcode)) {
            resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE, RspConst.SUCCESS_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, result.get("response"));
            log.info("Controller: ProjEmployeeController.queryEmployeeListByConditions() is successfully returned, result=" + resMap);
		} else {
			resMap.put(RspConst.HEADER, new Header(RspConst.API_FAIL_CODE, RspConst.API_FAIL_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, null);
			log.info("Controller: the response of remote api is not normally returned, result=" + resMap);
		}
		return resMap;
	}
	
	@RequestMapping("/queryAssociationList")
	@ResponseBody
	public Map<String, Object> queryAssociationList(@RequestBody Map<String,Object> paramMap) {
		log.info("Controller: ProjEmployeeController.queryEmployeeListByKeyword() is invoked. param=" + paramMap);
		// step1. 验证参数
		@SuppressWarnings("unchecked")
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		try
        {

            String keyword = (String) requestMap.get("keyword"); // 关键字
            // 验证keyword,验证类别EMPTY验证
            ParamUtils.validateParameter(queryMap, "keyword", keyword, ParamUtils.EMPTY);
  
        }
        catch (IllegalParameterException e)
        {
            log.info("Controller: illegal parameter. message>>>" + e.getMessage());

            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put(RspConst.HEADER, new Header(RspConst.ARGS_ERROR_CODE, RspConst.ARGS_ERROR_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, new Response("result", RspConst.ARGS_ERROR_MSG));

            log.info("Controller: ProjEmployeeController.queryAssociationList() is untimely returned, result=" + resMap);
            return resMap;
        }
		
		// step2. 请求服务层
		Map<String, Object> result = projEmployeeService.queryAssociationList(queryMap);
		
		// step3. 封装返回
		Map<String, Object> resMap = new HashMap<String, Object>();
		String rspcode =  (String) result.get("rspcode");
		if ("0000".equals(rspcode)) {
            resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE, RspConst.SUCCESS_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, result.get("response"));
            log.info("Controller: ProjEmployeeController.queryAssociationList() is successfully returned, result=" + resMap);
		} else {
			resMap.put(RspConst.HEADER, new Header(RspConst.API_FAIL_CODE, RspConst.API_FAIL_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, null);
			log.info("Controller: the response of remote api is not normally returned, result=" + resMap);
		}
		return resMap;
	}
	
	@RequestMapping("/addEmployeesToProject")
	@ResponseBody
	public Map<String, Object> addEmployeesToProject(@RequestBody Map<String,Object> paramMap) {
		log.info("Controller: ProjEmployeeController.addEmployeesToProject() is invoked. param=" + paramMap);
		// step1. 验证参数
		@SuppressWarnings("unchecked")
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		List<Map<String,Object>> employees = (List<Map<String, Object>>) requestMap.get("employees");
		
		try
        {
			String projectId = (String) requestMap.get("projectId"); // 项目id
			Map<String, Object> queryMap = new HashMap<String, Object>();
			if (StringUtils.isEmpty(projectId)) {
				throw new IllegalParameterException("projectId=" + projectId);
			}
            
            if (employees.isEmpty()) {
            	throw new IllegalParameterException("员工列表为空！");
            }
        }
        catch (IllegalParameterException e)
        {
            log.info("Controller: illegal parameter. message>>>" + e.getMessage());

            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put(RspConst.HEADER, new Header(RspConst.ARGS_ERROR_CODE, RspConst.ARGS_ERROR_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, new Response("result", RspConst.ARGS_ERROR_MSG));

            log.info("Controller: ProjEmployeeController.addEmployeesToProject() is untimely returned, result=" + resMap);
            return resMap;
        }
		
		// step2. 请求服务层
		boolean flag = projEmployeeService.addEmployeesToProject(requestMap);
		
		// step3. 封装返回
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		if (flag) {
            resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE, RspConst.SUCCESS_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, new Response("msg", RspConst.SUCCESS_MSG));
            log.info("Controller: ProjEmployeeController.addEmployeesToProject() is successfully returned, result=" + resMap);
		} else {
			resMap.put(RspConst.HEADER, new Header(RspConst.API_FAIL_CODE, RspConst.API_FAIL_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, null);
			log.info("Controller: the response of remote api is not normally returned, result=" + resMap);
		}
		return resMap;
	}
	
}
