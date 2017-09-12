package com.epro.pms.project_basic_info.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epro.pms.exception.CommonException;
import com.epro.pms.exception.IllegalParameterException;
import com.epro.pms.project_basic_info.po.EmpDic;
import com.epro.pms.project_basic_info.po.FpProject;
import com.epro.pms.project_basic_info.po.ProjCorePersonInfo;
import com.epro.pms.project_basic_info.po.ProjectBasicInfo;
import com.epro.pms.project_basic_info.po.ProjectStateInfo;
import com.epro.pms.project_basic_info.po.RiskProject;
import com.epro.pms.project_basic_info.service.FpProjectService;
import com.epro.pms.project_basic_info.service.IProjEmployeeService;
import com.epro.pms.project_basic_info.service.ProjCorePersonService;
import com.epro.pms.project_basic_info.service.ProjStateService;
import com.epro.pms.project_basic_info.service.ProjectService;
import com.epro.pms.project_basic_info.service.RiskProjectService;
import com.epro.pms.util.JSONUtils;
import com.epro.pms.util.ParamUtils;
import com.epro.pms.util.po.Header;
import com.epro.pms.util.po.Response;
import com.epro.pms.util.po.RspConst;

import net.sf.json.JSONObject;

/**
 * 
 * @author qinshiguang
 * 项目信息的控制类
 */
@Controller
@RequestMapping("/project")
public class ProjectController
{
	private static Log log = LogFactory.getLog(ProjectController.class);
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjStateService projStateService;
	
	@Autowired
	private ProjCorePersonService projCoreService;
	
	@Autowired
	private RiskProjectService riskProjectService;
	
	@Autowired
	private FpProjectService fpProjectService;
	
	@Autowired
	private IProjEmployeeService projEmployeeService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/softDelBasicInfo.do", method=RequestMethod.POST)
	public Map<String, Object> softDelBasicInfo(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into softDelBasicInfo method...");
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Integer projectId = Integer.valueOf(requestMap.get("projectId").toString());
		
		// 更新fp项目数据
		fpProjectService.deleteFpProjectByPK(projectId);
		// 更新risk项目数据
		riskProjectService.deleteRiskProjectByPK(projectId);
		// 项目状态数据处理
		projStateService.deleteProjStateByPK(projectId);
		// 更新项目数据
		projectService.softDelProjectByPK(projectId);
		// 删除人员，并更新hr系统相关信息
		projCoreService.deleteProjCorePerson(projectId);
		
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put("projectId", projectId);
		List<Map<String, Object>> delEmps = projectService.delEmpsFromProject(bean);
		Map<String, Object> delEmp = null;
		Map<String, Object> delBeans = new HashMap<String, Object>();
		Map<String, Object> delBean = new HashMap<String, Object>();
		for (int i = 0; i < delEmps.size(); i++)
		{
			delEmp = delEmps.get(i);
			delBean.put("jobNumber", delEmp.get("employeeId"));
			delBeans.put("employee", delBean);
			projEmployeeService.deleteEmployeeFromProject(delBeans);
		}
		
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", projectId));
		
		log.info("leave softDelBasicInfo method...");
		return resMap;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listBasicInfo.do", method=RequestMethod.POST)
	public Map<String, Object> listBasicInfo(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		// 转换成数字
		int beginIndex =  Integer.parseInt(headerMap.get("beginnum").toString());
		int endIndex = Integer.parseInt(headerMap.get("endnum").toString());
		
		// 总记录数的获取
		requestMap.put("status", 1);
		List<Map<String, Object>> projects = projectService.getProjects(requestMap);
		int totalRecord = projects.size();							
				
		// 分页查询条件
		requestMap.put("beginnum", beginIndex);				// 开始数
		requestMap.put("pageRecord", endIndex - beginIndex);// 查询条数
		
		projects.clear();
		projects = projectService.getProjects(requestMap);
		
		//封装响应信息
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,
												RspConst.SUCCESS_MSG,
												endIndex - beginIndex,
												projects.size(),
												totalRecord));
		resMap.put(RspConst.RESPONSE,new Response("result", projects));
		
		return resMap;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listBasicState", method=RequestMethod.POST)
	public Map<String, Object> listBasicState(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		// 转换成数字
		int beginIndex =  Integer.parseInt(headerMap.get("beginnum").toString());
		int endIndex = Integer.parseInt(headerMap.get("endnum").toString());
		
		// 总记录数的获取
		requestMap.put("status", 1);
		List<Map<String, Object>> projectState = projectService.getProjectState(requestMap);
		int totalRecord = projectState.size();							
				
		// 分页查询条件
		requestMap.put("beginnum", beginIndex);				// 开始数
		requestMap.put("pageRecord", endIndex - beginIndex);// 查询条数
		
		projectState.clear();
		projectState = projectService.getProjectState(requestMap);
		
		// 返回数据
		Map<String, Object> c = new HashMap<String, Object>();
		c.put("list", projectState);
		//封装响应信息
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,
												RspConst.SUCCESS_MSG,
												endIndex - beginIndex,
												projectState.size(),
												totalRecord));
		resMap.put(RspConst.RESPONSE,new Response("result", c));
		
		return resMap;
	}
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/initAddProject.do", method=RequestMethod.POST)
	public Map<String, Object> initAddProject(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("in addbasic method...");
		
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Map<String, Object> map = new HashMap<String, Object>();
		requestMap.put("status", 1);
		
		// 根据项目名称，获取项目信息，且唯一
		List<Map<String, Object>> project = projectService.getProjects(requestMap);
		// 如果项目信息存在，获取项目状态信息
		if (project.size() > 0)
		{
			ProjectStateInfo projectState = projStateService.getProjectStateByPK(Integer.valueOf((String) project.get(0).get("projectId")));
			map.put("project", project.get(0));
			map.put("projectState", projectState);
		}
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", map));
		
		log.info("leave addbasic method...");
		return resMap;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/initEdit", method=RequestMethod.POST)
	public Map<String, Object> initEdit(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("in addbasic method...");
		
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Map<String, Object> map = new HashMap<String, Object>();
//		requestMap.put("status", 1);
		Integer projectId = Integer.valueOf(requestMap.get("projectId").toString());
		int roleType =  Integer.parseInt(headerMap.get("systype").toString());
		//QA的角色类型为2 只能维护数据状态，所以只查出状态
		if(roleType!=2){
			// 根据项目名称，获取项目信息，且唯一
			ProjectBasicInfo project = projectService.getProjectByPK(projectId);
			// 获取合同的部分信息
			Map<String, Object> contract = projectService.getContractByPK(project.getContractId());
			map.put("project", project);
			map.put("contract", contract);
		}		
		// 如果项目信息存在，获取项目状态信息
		ProjectStateInfo projectState = projStateService.getProjectStateByPK(projectId);
		
		map.put("projectState", projectState);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", map));
		
		log.info("leave addbasic method...");
		return resMap;
	}
	/**
	 * 添加项目的方法
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/addBasic.do", method=RequestMethod.POST)
	public Map<String, Object> addBasic(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("in addbasic method...");
		// step1.获取参数，验证参数
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		
		Integer projectId;
		requestMap.put("finishTime", StringUtils.isEmpty(requestMap.get("finishTime").toString()) ? 
							new Date() : requestMap.get("finishTime"));
		/*
		 * 传过来的参数若有projectId，
		 * 则进行更新操作，
		 * 否则进行新增操作 
		 */
		if ( StringUtils.isEmpty(requestMap.get("projectId").toString()) )
		{
			log.info("########## insert #########");
			projectService.addProject(requestMap);
			
			// 获取项目ID，为后面的修改操作铺路
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("contractId", requestMap.get("contractId"));
			condition.put("status", 1);
			List<Map<String, Object>> project = projectService.getProjects(condition);
			projectId = (Integer) project.get(0).get("projectId");
			
		}
		// 修改项目基本信息
		else 
		{
			log.info("############ update ###########");
			projectId = Integer.valueOf(requestMap.get("projectId").toString());
			projectService.updateProject(requestMap);
		}
		
		// risk项目
		if ("1".equals(requestMap.get("isTopProject")) && "差".equals(requestMap.get("projHealthLevel")))
		{
			Map<String, Object> riskProject = new HashMap<String, Object>();
			riskProject.put("projectId", projectId);
			riskProject.put("projectName", requestMap.get("projectName"));
			riskProject.put("orderlies", requestMap.get("ePmName"));
			riskProject.put("projVersion", "v1.0");
			riskProject.put("riskType", "重点/风险项目");
			if (riskProjectService.getRiskProjectByPK(projectId) == null)
			{
				riskProject.put("riskIssues", "");
				riskProject.put("poStatusRisk", "");
				riskProject.put("riskStatus", "开启");
				riskProject.put("isRiskAscend", "0");
				riskProjectService.addRiskProject(riskProject);
			}
			else 
				riskProjectService.updateRiskProjectByPK(riskProject);
		}
		else if ("1".equals(requestMap.get("isTopProject")))
		{
			Map<String, Object> riskProject = new HashMap<String, Object>();
			riskProject.put("projectId", projectId);
			riskProject.put("projectName", requestMap.get("projectName"));
			riskProject.put("orderlies", requestMap.get("ePmName"));
			riskProject.put("projVersion", "v1.0");
			riskProject.put("riskType", "重点项目");
			if (riskProjectService.getRiskProjectByPK(projectId) == null)
			{
				riskProject.put("riskIssues", "");
				riskProject.put("poStatusRisk", "");
				riskProject.put("riskStatus", "开启");
				riskProject.put("isRiskAscend", "0");
				riskProjectService.addRiskProject(riskProject);
			}
			else 
				riskProjectService.updateRiskProjectByPK(riskProject);
			
		}
		else if ("差".equals(requestMap.get("projHealthLevel")))
		{
			Map<String, Object> riskProject = new HashMap<String, Object>();
			riskProject.put("projectId", projectId);
			riskProject.put("projectName", requestMap.get("projectName"));
			riskProject.put("orderlies", requestMap.get("ePmName"));
			riskProject.put("projVersion", "v1.0");
			riskProject.put("riskType", "风险项目");
			if (riskProjectService.getRiskProjectByPK(projectId) == null)
			{
				riskProject.put("riskIssues", "");
				riskProject.put("poStatusRisk", "");
				riskProject.put("riskStatus", "开启");
				riskProject.put("isRiskAscend", "0");
				riskProjectService.addRiskProject(riskProject);
			}
			else 
				riskProjectService.updateRiskProjectByPK(riskProject);
		}
		
		else 
		{
			riskProjectService.deleteRiskProjectByPK(projectId);
		}
		
		
		// 更新合同项目状态
		Map<String, Object> contractInfo = new HashMap<String, Object>();
		contractInfo.put("contractId", requestMap.get("contractId"));
		contractInfo.put("contractState", 2);
		projectService.updateContract(contractInfo);
		
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", projectId));
		
		log.info("leave addbasic method...");
		return resMap;
	}
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/addState.do", method=RequestMethod.POST)
	public Map<String, Object> addState(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("in addState method...");
		Map<String, Object> executeCode = new HashMap<String, Object>();
		executeCode.put("health", false);
		// step1.获取参数，验证参数
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Integer projectId = Integer.valueOf(requestMap.get("projectId").toString());
		
		projStateService.deleteProjStateByPK(projectId);
		projStateService.addProjState(requestMap);
		
		if (!"1".equals(requestMap.get("qualiyDesc")) && "良".equals(requestMap.get("projHealthLevel")))
		{
			Map<String, Object> bean = new HashMap<String, Object>();
			bean.put("projectId", projectId);
			bean.put("projHealthLevel", "一般");
			projectService.updateProject(bean);
			executeCode.put("health", true);
		}
		
		int roleType = 0;
		if (StringUtils.isNumeric(headerMap.get("systype").toString()) && StringUtils.isNotEmpty(headerMap.get("systype").toString()))
			roleType =  Integer.parseInt(headerMap.get("systype").toString());
		//QA的角色类型为2 只能维护数据状态，所以只查出状态
		if(roleType == 2){
			Map<String, Object> resMap = new HashMap<String, Object>();
			resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
			resMap.put(RspConst.RESPONSE,new Response("result", executeCode));
			
			log.info("leave addState method...");
			return resMap;
		}
		
		// fp项目
		if ("1".equals(requestMap.get("projectType")))
		{
			Map<String, Object> fpProject = new HashMap<String, Object>();
			fpProject.put("projectId", projectId);
			fpProject.put("projectName", requestMap.get("projectName"));
			fpProject.put("transitionStartTime", new Date());
			fpProject.put("transitionEndTime", 
					StringUtils.isEmpty(requestMap.get("finishTime").toString()) ? 
							new Date() : requestMap.get("finishTime"));
			fpProject.put("bAmount", requestMap.get("currWork"));
			
			if (fpProjectService.getFpProjectByPK(projectId) == null)
			{
				fpProject.put("plalProgress", "0.00");
				fpProject.put("currProgress", "0.00");
				fpProject.put("riskIssues", "");
				fpProject.put("poStatusRisk", "");
				fpProject.put("plalCost", 0);
				fpProject.put("currCost", 0);
				fpProjectService.addFpProject(fpProject);
			}
			else 
				fpProjectService.updateFpProject(fpProject);
			
		}
		else 
		{
			fpProjectService.deleteFpProjectByPK(projectId);
		}

		
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", executeCode));
		
		log.info("leave addState method...");
		return resMap;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/updateProjectTeam", method = RequestMethod.POST)
	public Map<String, Object> updateProjectTeam(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into updateCountForProject...");
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
            String projTeamId = (String) requestMap.get("projTeamId");
            
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
            
            ParamUtils.validateParameter(queryMap, "projTeamId", projTeamId, ParamUtils.NULL);
            
            queryMap.remove("projTeamId");
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
			Map<String, Object> response = (Map<String, Object>) result.get("response");
			List<Map<String, Object>> employees = (List<Map<String, Object>>) response.get("employees");
			
			
			List<Map<String, Object>> emps4team = new ArrayList<Map<String,Object>>();
			Map<String, Object> emp4team;
			for (int i = 0; i < employees.size(); i++) 
			{
				emp4team = new HashMap<String, Object>();
				emp4team.put("eproNo",  employees.get(i).get("jobNumber"));
				emp4team.put("name",  employees.get(i).get("personName"));
				emps4team.add(emp4team);
			}
			
			JSONObject emps4json = new JSONObject();
			emps4json.put("employees", emps4team);
			
			String jsonString = JSONUtils.toJSONString(emps4json);
			
			Map<String, Object> bean = new HashMap<String, Object>();
			bean.put("teamMember", jsonString);
			bean.put("projTeamId", requestMap.get("projTeamId"));
			
			// 更新项目组人员信息
			Map<String, Object> updateProjectteamPerson = projectService.updateProjectteamPersonById(bean);
			if ("0000".equals(updateProjectteamPerson.get("header").toString()))
			{
				resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE, RspConst.SUCCESS_MSG, 0, 0, 0));
				resMap.put(RspConst.RESPONSE, new Response("result", null));
				log.info("Controller: ProjEmployeeController.queryEmployeeListByConditions() is successfully returned, result=" + resMap);
			}
			else
			{
				resMap.put(RspConst.HEADER, new Header(RspConst.CONTINUE_REQUEST_CODE, RspConst.CONTINUE_REQUEST_MSG, 0, 0, 0));
				resMap.put(RspConst.RESPONSE, new Response("result", updateProjectteamPerson.get("employees")));
				log.info("Controller: ProjEmployeeController.queryEmployeeListByConditions() is successfully returned, result=" + resMap);
			}
			
		} else {
			resMap.put(RspConst.HEADER, new Header(RspConst.API_FAIL_CODE, RspConst.API_FAIL_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, new Response("result", null));
			log.info("Controller: the response of remote api is not normally returned, result=" + resMap);
		}

        log.info("Controller: ProjEmployeeController.queryEmployeeListByConditions() is returned, result=" + resMap);
        return resMap;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getProjectTeamEmployees")
	public Map<String, Object> getProjectTeamEmployees(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into getProjectTeamEmployees...");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		
		
		try
        {
            String projectId = (String) requestMap.get("projectId");
            String projTeamId = (String) requestMap.get("projTeamId");
            
            ParamUtils.validateParameter(queryMap, "projectId", projectId, ParamUtils.NULL);
            ParamUtils.validateParameter(queryMap, "projTeamId", projTeamId, ParamUtils.NULL);
            
        }
        catch (IllegalParameterException e)
        {
            log.info("Controller: illegal parameter. message>>>" + e.getMessage());

            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put(RspConst.HEADER, new Header(RspConst.ARGS_ERROR_CODE, RspConst.ARGS_ERROR_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, new Response("result", RspConst.ARGS_ERROR_MSG));

            log.info("Controller: getProjectTeamEmployees is untimely returned, result=" + resMap);
            return resMap;
        }
		
		
		Map<String,Object> resp = projectService.getProjectTeamEmployees(queryMap);
		
		if ("0000".equals(resp.get("header").toString()))
		{
			Map<String, Object> resMap = new HashMap<String, Object>();
	        resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE, RspConst.SUCCESS_MSG, 0, 0, 0));
	        resMap.put(RspConst.RESPONSE, new Response("result", resp.get("body")));

	        log.info("Controller: getProjectTeamEmployees is untimely returned, result=" + resMap);
	        return resMap;
		}
		
		
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE, RspConst.SUCCESS_MSG, 0, 0, 0));
        resMap.put(RspConst.RESPONSE, new Response("result", null));

        log.info("Controller: getProjectTeamEmployees is untimely returned, result=" + resMap);
        return resMap;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/addEmpsToProject", method = RequestMethod.POST)
	public Map<String, Object> addEmpsToProject(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into updateCountForProject...");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		
		
		try
        {
            String projectId = (String) requestMap.get("projectId");
            String projTeamId = (String) requestMap.get("projTeamId");
            String projectPO = (String) requestMap.get("projectPO");
            List<Map<String, Object>> persons = (List<Map<String, Object>>) requestMap.get("persons");
            if (persons.size() == 0)
            {
            	throw new IllegalParameterException("无人员信息...");
            }
            queryMap.put("persons", persons);
            queryMap.put("projectPO", projectPO);
            ParamUtils.validateParameter(queryMap, "projectId", projectId, ParamUtils.NULL);
            ParamUtils.validateParameter(queryMap, "projTeamId", projTeamId, ParamUtils.NULL);
            
        }
        catch (IllegalParameterException e)
        {
            log.info("Controller: illegal parameter. message>>>" + e.getMessage());

            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put(RspConst.HEADER, new Header(RspConst.ARGS_ERROR_CODE, RspConst.ARGS_ERROR_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, new Response("result", RspConst.ARGS_ERROR_MSG));

            log.info("Controller: getProjectTeamEmployees is untimely returned, result=" + resMap);
            return resMap;
        }
		
		projectService.addEmpsToProject(queryMap);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", null));
		
		log.info("leave addEmpsToProject() method...");
		return resMap;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/delEmpFromProject", method = RequestMethod.POST)
	public Map<String, Object> delEmpFromProject(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into updateCountForProject...");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		
		Map<String, Object> employee = (Map<String, Object>) requestMap.get("employee"); 	// 
		
		
		projectService.delEmpFromProject(employee);
            
            
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", null));
		
		log.info("leave delEmpFromProject() method...");
		return resMap;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/delEmpsFromProject", method = RequestMethod.POST)
	public Map<String, Object> delEmpsFromProject(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into updateCountForProject...");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		
		try
        {
            String belongcompany = (String) requestMap.get("belongcompany"); 	// 
            String projectteam = (String) requestMap.get("projectteam"); 	// 
            String productline = (String) requestMap.get("productline"); 	// 
            String projTeamId = (String) requestMap.get("projTeamId");
            
            queryMap.put("beginnum", 0);
        	queryMap.put("endnum", 100);
            // 验证belongcompany,验证类别EMPTY验证
            ParamUtils.validateParameter(queryMap, "belongcompany", belongcompany, ParamUtils.NULL);
            // 验证projectteam,验证类别EMPTY验证
            ParamUtils.validateParameter(queryMap, "projectteam", projectteam, ParamUtils.NULL);
            // 验证productline,验证类别EMPTY验证
            ParamUtils.validateParameter(queryMap, "productline", productline, ParamUtils.NULL);
            
            ParamUtils.validateParameter(queryMap, "projTeamId", projTeamId, ParamUtils.NULL);
            
            queryMap.remove("projTeamId");
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
			Map<String, Object> response = (Map<String, Object>) result.get("response");
			List<Map<String, Object>> employees = (List<Map<String, Object>>) response.get("employees");
			
			
			List<Map<String, Object>> emps4team = new ArrayList<Map<String,Object>>();
			Map<String, Object> emp4team;
			for (int i = 0; i < employees.size(); i++) 
			{
				emp4team = new HashMap<String, Object>();
				emp4team.put("eproNo",  employees.get(i).get("jobNumber"));
				emp4team.put("name",  employees.get(i).get("personName"));
				emps4team.add(emp4team);
			}
			
			JSONObject emps4json = new JSONObject();
			emps4json.put("employees", emps4team);
			
			String jsonString = JSONUtils.toJSONString(emps4json);
			
			Map<String, Object> bean = new HashMap<String, Object>();
			bean.put("teamMember", jsonString);
			bean.put("projTeamId", requestMap.get("projTeamId"));
			
			// 更新项目组人员信息
			projectService.hardUpdateProjectteamPersonById(bean);
			
			// 从项目中移除人员
			List<Map<String, Object>> emps = (List<Map<String, Object>>) requestMap.get("employees"); 	//
			
			Map<String, Object> bean1 = new HashMap<String, Object>();
			for (int i = 0; i < emps.size(); i++)
			{
				bean1.put("jobNumber", emps.get(i).get("eproNo"));
				projectService.delEmpFromProject(bean1);
			}
			
		} else {
			resMap.put(RspConst.HEADER, new Header(RspConst.API_FAIL_CODE, RspConst.API_FAIL_MSG, 0, 0, 0));
            resMap.put(RspConst.RESPONSE, new Response("result", null));
			log.info("Controller: the response of remote api is not normally returned, result=" + resMap);
		}

		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE, RspConst.SUCCESS_MSG, 0, 0, 0));
		resMap.put(RspConst.RESPONSE, new Response("result", null));
		log.info("Controller: ProjEmployeeController.queryEmployeeListByConditions() is successfully returned, result=" + resMap);
		
		log.info("leave delEmpFromProject() method...");
		return resMap;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/updateCount", method = RequestMethod.POST)
	public Map<String, Object> updateCountForProject(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into updateCountForProject...");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		
        String projectId = (String) requestMap.get("projectId");
        
        queryMap.put("projectId", projectId);
		
		// step2. 请求服务层
        List<Map<String,Object>> projectEmps = projectService.getProjectEmps(queryMap);
		
		// 更新的项目信息
		Map<String, Object> updateData = new HashMap<String, Object>();
		updateData.put("projectId", projectId);
		updateData.put("count", projectEmps.size());
		
		projectService.updateProject(updateData);
		
		
		// step3. 封装返回
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.API_FAIL_CODE, RspConst.API_FAIL_MSG, 0, 0, 0));
        resMap.put(RspConst.RESPONSE, null);
		log.info("Controller: the response of remote api is not normally returned, result=" + resMap);
		return resMap;
	}
	
	
	/**
	 * 重点/风险项目
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/listRisk.do", method=RequestMethod.POST)
	public Map<String, Object> listRisk(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into addRisk() method...");
		// step1.获取参数，验证参数
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		
		int beginIndex = Integer.valueOf(headerMap.get("beginnum").toString());
		int endIndex = Integer.valueOf(headerMap.get("endnum").toString());
		
		List<Map<String, Object>> riskProjects = riskProjectService.getRiskProject(requestMap);
		int totalRecord = riskProjects.size();
		
		// 分页查询条件
		requestMap.put("beginnum", beginIndex);				// 开始数
		requestMap.put("pageRecord", endIndex - beginIndex);// 查询条数
		
		riskProjects.clear();
		riskProjects = riskProjectService.getRiskProject(requestMap);
		
		
		//封装响应信息
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,
												RspConst.SUCCESS_MSG,
												endIndex - beginIndex,
												riskProjects.size(),
												totalRecord));
		resMap.put(RspConst.RESPONSE,new Response("result", riskProjects));
		
		log.info("leave addRisk() method...");
		return resMap;
	}
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/delRisk.do", method=RequestMethod.POST)
	public Map<String, Object> delRisk(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into delRisk() method...");
		//获取参数
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		
		Map<String, Object> condition = new HashMap<String, Object>();
		Integer projectId =  Integer.valueOf(requestMap.get("projectId").toString());
		condition.put("projectId", projectId);
		condition.put("isTopProject", 0);
		if (!"重点项目".equals(requestMap.get("riskType")))
		{
			condition.put("projHealthLevel", "良");
		}
		
		riskProjectService.deleteRiskProjectByPK(projectId);
		// 更新项目数据
		projectService.updateProject(condition);
		
		//获取重点风险项目信息，放到容器里保存
		Map<String, Object> c = new HashMap<String, Object>();
		
		c.put("list", null);
		
		//封装响应信息
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", c));
		
		log.info("leave delRisk() method...");
		return resMap;
	}
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/editRisk.do", method = RequestMethod.POST)
	public Map<String, Object> editRisk(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into editFp() method...");
		// 获取参数
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		
		riskProjectService.updateRiskProjectByPK(requestMap);
		
		//封装响应信息
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));

		log.info("leave editFp() method...");
		return resMap;
	}
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/listFp.do", method = RequestMethod.POST)
	public Map<String, Object> listFp(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into listFp() method...");
		// 获取参数
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		
		int beginIndex = Integer.valueOf(headerMap.get("beginnum").toString());
		int endIndex = Integer.valueOf(headerMap.get("endnum").toString());
		
		
		List<Map<String, Object>> list = fpProjectService.getFpProject(requestMap);
		int totalRecord = list.size();
		
		// 分页查询条件
		requestMap.put("beginnum", beginIndex);				// 开始数
		requestMap.put("pageRecord", endIndex - beginIndex);// 查询条数
		
		list.clear();
		list = fpProjectService.getFpProject(requestMap);
		// 返回数据
		//封装响应信息
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,
												RspConst.SUCCESS_MSG,
												endIndex - beginIndex,
												list.size(),
												totalRecord));
		resMap.put(RspConst.RESPONSE,new Response("result", list));
		
		log.info("leave listFp() method...");
		return resMap;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/delFp.do", method = RequestMethod.POST)
	public Map<String, Object> delFp(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into listFp() method...");
		// 获取参数
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		
		Map<String, Object> condition = new HashMap<String, Object>();
		Integer projectId =  Integer.valueOf(requestMap.get("projectId").toString());
		condition.put("projectId", projectId);
		condition.put("projectType", 3);
		
		fpProjectService.deleteFpProjectByPK(projectId);
		// 更新项目信息, 由原FP项目改为TM项目
		projectService.updateProject(condition);

		//封装响应信息
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new
		Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));

		log.info("leave listFp() method...");
		return resMap;
	}
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/editFp.do", method = RequestMethod.POST)
	public Map<String, Object> editFp(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into editFp() method...");
		// 获取参数
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		/*
		 * 获取合同时间
		 * 
		 */
		Map<String, Object> contractCondition = new HashMap<String, Object>();
		contractCondition.put("contractId", requestMap.get("contractId"));
		Map<String, Object> contractInfo = fpProjectService.getContractInfo(contractCondition);
		Date startTime = (Date) contractInfo.get("startTime");
		Date endTime = (Date) contractInfo.get("endTime");
		Date now  = new Date();
		/*
		 * 获取项目状态信息
		 * 
		 */
		Map<String, Object> projectCondition = new HashMap<String, Object>();
		projectCondition.put("projectId", requestMap.get("projectId"));
		Map<String, Object> fpProjectState = fpProjectService.getFpProjectState(projectCondition);
		int currWork = Integer.parseInt(fpProjectState.get("currWork").toString());
		/*
		 * 获取项目实际的工作量
		 * 
		 */
		Map<String, Object> workloadBean = fpProjectService.getWorkload(projectCondition);
		int allworkload = workloadBean == null ? 0 : (int) Float.parseFloat(workloadBean.get("allworkload").toString());
		
		if (now.compareTo(startTime) < 0)
		{
			requestMap.put("plalProgress", "0.00");
			requestMap.put("plalCost", 0);
		}
		else if (now.compareTo(endTime) > 0)
		{
			requestMap.put("plalProgress", "100.00");
			requestMap.put("plalCost", currWork);
		}
		if (startTime.compareTo(endTime) < 0 && now.compareTo(startTime) > 0 && now.compareTo(endTime) < 0)
		{
			float planProgress = 0.0f;
			float total = endTime.getTime() - startTime.getTime();
			float plan  = now.getTime()-startTime.getTime();
			planProgress = (float)((int)(plan * 10000 / total)) / 100;
			requestMap.put("plalProgress", planProgress);
			int planCost =  (int) (planProgress * currWork / 100);
			requestMap.put("plalCost", planCost);
		}
		requestMap.put("currCost", allworkload);
		
		fpProjectService.updateFpProject(requestMap);
		
		//封装响应信息
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new
		Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));

		log.info("leave editFp() method...");
		return resMap;
	}
	/**
	 * @desc 详情查询的借口，获取项目基本信息
	 *       项目状态信息
	 *       核心人员信息
	 * @param paramMap
	 * @return
	 * @throws CommonException
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getBasic", method = RequestMethod.POST)
	public Map<String, Object> getBasic(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into getBasic() method...");
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Integer projectId = Integer.valueOf(requestMap.get("projectId").toString());
		
		// 获取项目基本信息
		Map<String, Object> project = projectService.getBasic(requestMap);
		ProjectStateInfo state = projStateService.getProjectStateByPK(projectId);
		
		/*
		 * 获取项目人员信息 
		 */
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put("projectId", projectId);
		List<Map<String,Object>> projectEmps = projectService.getProjectEmps(bean);
		
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("project", project);
		result.put("state", state);
		result.put("projectEmps", projectEmps);
		
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", result));
		
		log.info("leave getBasic() method...");
		return resMap;
	}
	
	/**
	 * @desc 详情查询的借口，获取项目基本信息
	 *       项目状态信息
	 *       核心人员信息
	 * @param paramMap
	 * @return
	 * @throws CommonException
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getBasic2", method = RequestMethod.POST)
	public Map<String, Object> getBasic2(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into getBasic() method...");
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		Integer projectId = Integer.valueOf(requestMap.get("projectId").toString());
		
		Map<String, Object> project = projectService.getBasic(requestMap);
		ProjectStateInfo state = projStateService.getProjectStateByPK(projectId);
		
		try
		{
			List<Map<String, Object>> corePerson = projCoreService.getProjCorePersonByProjectId(projectId);
			
			Map<String, Object> empsCondition = new HashMap<String, Object>();
			empsCondition.put("projectteam", project.get("projTeamName"));
			empsCondition.put("productline", project.get("productLine"));
			empsCondition.put("belongcompany", "");
			empsCondition.put("beginnum", 0);
			empsCondition.put("endnum", 100);
			
			Map<String, Object> resp = projEmployeeService.queryEmployeeListByConditions(empsCondition);
			Map<String, Object> response = (Map<String, Object>) resp.get("response");
			List<Map<String, Object>> emps = (List<Map<String, Object>>) response.get("employees");
			Map<String, Object> emp;
			/*
			 * 
			 * 员工信息与核心骨干信息拼接
			 * 
			 */
			for (int i = 0; i < emps.size(); i++)
			{
				emp = emps.get(i);
				if ("1".equals(emp.get("isbackbone")))
				{
					for (int j = 0; j < corePerson.size(); j++)
					{
						if (emp.get("jobNumber").equals(corePerson.get(j).get("eproNo")))
						{
							emp.put("isSeperation", corePerson.get(j).get("isSeperation"));
							emp.put("seperationLevel", corePerson.get(j).get("seperationLevel"));
							emp.put("desc", corePerson.get(j).get("desc"));
							emp.put("stabilityMeasure", corePerson.get(j).get("stabilityMeasure"));
							break;
						}
					}
				}
				else 
				{
					emp.put("isSeperation", "");
					emp.put("seperationLevel", "");
					emp.put("desc", "");
					emp.put("stabilityMeasure", "");
				}
			}
			
			Map<String, Object> resMap = new HashMap<String, Object>();
			Map<String, Object> result = new HashMap<String, Object>();
			
			result.put("project", project);
			result.put("state", state);
//			result.put("corePerson", corePerson);
			result.put("employees", emps);
			
			resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
			resMap.put(RspConst.RESPONSE,new Response("result", result));
			
			log.info("leave getBasic() method...");
			return resMap;
			
		} catch (Exception e)
		{
			// TODO: handle exception
			Map<String, Object> resMap = new HashMap<String, Object>();
			Map<String, Object> result = new HashMap<String, Object>();
			
			result.put("project", project);
			result.put("state", state);
			result.put("employees", null);
			
			resMap.put(RspConst.HEADER, new Header(RspConst.API_FAIL_CODE,RspConst.API_FAIL_MSG,1,1,1));
			resMap.put(RspConst.RESPONSE,new Response("result", result));
			
			log.info("invoking hr api error: " + e.getMessage());
			return resMap;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getCorePerson", method = RequestMethod.POST)
	public Map<String, Object> getCorePerson(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into getCorePerson() method...");
		Map<String, Object> header = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> request = (Map<String, Object>) paramMap.get("request");
		
		Integer projectId =  Integer.valueOf(request.get("projectId").toString());
		
		if (StringUtils.isEmpty(request.get("projectId").toString()))
		{
			throw new IllegalArgumentException("projectId is empty");
		}
		
		List<Map<String, Object>> corePerson = projCoreService.getProjCorePersonByProjectId(projectId);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("corePerson", corePerson);
		
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", result));
		
		log.info("leave getCorePerson() method...");
		return resMap;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/updateCorePerson", method = RequestMethod.POST)
	public Map<String, Object> updateCorePerson(@RequestBody Map<String, Object> paramMap) throws CommonException
	{
		log.info("into getCorePerson() method...");
		Map<String, Object> header = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> request = (Map<String, Object>) paramMap.get("request");
		
		
		projCoreService.updateProjCorePerson(request);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result", null));
		
		log.info("leave getCorePerson() method...");
		return resMap;
	}
	
	/**
	 * @author epro
	 * @deprecated 增加项目查询合同表中所需要的字段
	 * @param paramMap
	 * @return 
	 * @throws CommonException
	 */
	@ResponseBody
	@RequestMapping(value="/getContract4project2",method = RequestMethod.POST) 
	public Map<String, Object> getContract4project(@RequestBody Map<String, Object> paramMap) throws CommonException {
		log.info("------进入ContractController--------getContract4project方法---------");
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = projectService.getContract4project(requestMap);
		map.put("list", list);
		// step3. 封装返回结果
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result",map));
		log.info("------退出ContractController--------getContract4project方法---------");
		return resMap;
	}
	
}
















