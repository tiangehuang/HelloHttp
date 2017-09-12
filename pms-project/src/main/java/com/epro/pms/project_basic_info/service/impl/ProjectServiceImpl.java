package com.epro.pms.project_basic_info.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epro.pms.exception.CommonException;
import com.epro.pms.project_basic_info.dao.ProjectBasicInfoMapper;
import com.epro.pms.project_basic_info.dao.ProjectTeamEmployeeMapper;
import com.epro.pms.project_basic_info.po.EmpDic;
import com.epro.pms.project_basic_info.po.ProjectBasicInfo;
import com.epro.pms.project_basic_info.service.ProjectService;
import com.epro.pms.util.JSONUtils;
import com.epro.pms.util.Page;

import net.sf.json.JSONObject;
/**
 * 
 * @author qinshiguang
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService
{
	private static final Logger log = Logger.getLogger(ProjectServiceImpl.class);
	@Autowired
	private ProjectBasicInfoMapper projectBasicInfoMapper;
	
	@Autowired
	private ProjectTeamEmployeeMapper projectTeamEmployee;
	
	
	@Override
	public void addProject(Map<String, Object> projectBasicInfo) throws CommonException
	{
		projectBasicInfoMapper.insert(projectBasicInfo);
		
	}

	@Override
	public void deleteProject(Integer projectId) throws CommonException
	{
		projectBasicInfoMapper.deleteProjectBasicInfoByPK(projectId);
		
	}

	@Override
	public void updateProject(Map<String, Object> projectBasicInfo) throws CommonException
	{
		projectBasicInfoMapper.updateProjectBasicInfoByPK(projectBasicInfo);
		
	}

	@Override
	public ProjectBasicInfo getProjectByPK(Integer projectId) throws CommonException
	{
		ProjectBasicInfo projectBasicInfo = projectBasicInfoMapper.getProjectBasicInfoByPK(projectId);
		return projectBasicInfo;
	}

	@Override
	public List<Map<String, Object>> getProjects(Map<String, Object> condition) throws CommonException
	{
		List<Map<String, Object>> projectBasicInfo = projectBasicInfoMapper.getProjectBasicInfo(condition);
		return projectBasicInfo;
	}

	@Override
	public void softDelProjectByPK(Integer projectId)
	{
		projectBasicInfoMapper.softDelProjectByPK(projectId);
		
	}

	@Override
	public Map<String, Object> getBasic(Map<String, Object> project)
	{
		
		return projectBasicInfoMapper.getBasic(project);
	}

	@Override
	public void updateContract(Map<String, Object> contract)
	{
		projectBasicInfoMapper.updateContract(contract);
		
	}

	@Override
	public Map<String, Object> getContractByPK(Integer contractId)
	{
		Map<String, Object> contract = projectBasicInfoMapper.getContractByPK(contractId);
		return contract;
	}

	@Override
	public List<Map<String, Object>> getProjectState(Map<String, Object> condition)
	{
		List<Map<String, Object>> projectState = projectBasicInfoMapper.getProjectState(condition);
		return projectState;
	}
	
	@Override
	public void hardUpdateProjectteamPersonById(Map<String, Object> bean)
	{
		projectTeamEmployee.updateProjectteamPersonById(bean);
		
	}

	@Override
	public Map<String, Object> updateProjectteamPersonById(Map<String, Object> bean)
	{
		log.info("in ProjectServiceImpl.updateProjectteamPersonById" + bean);
		Map<String, Object> respon = new HashMap<String, Object>();
		// 获取项目组人员信息
		String projTeamId = (String) bean.get("projTeamId");
		Map<String, Object> previos = new HashMap<String, Object>();
		previos.put("projTeamId", projTeamId);
		Map<String, Object> projectteamPerson = projectTeamEmployee.getProjectteamPersonById(previos);
		
		/*
		 * 本地项目组没有人员的情况下，
		 * 直接更新数据。 
		 */
		// 第一次更新
		if (projectteamPerson == null || StringUtils.isEmpty(projectteamPerson.get("teamMember").toString())) 
		{
			projectTeamEmployee.updateProjectteamPersonById(bean);
			respon.put("header", "0000");
			respon.put("employees", "");
			log.info("out ProjectServiceImpl.updateProjectteamPersonById" + respon);
			return respon;
		}
		// 解析json数据
		String localEmpsJson = projectteamPerson.get("teamMember").toString();
		JSONObject obj = JSONObject.fromObject(localEmpsJson);
		Object object = obj.get("employees");
		List<Map<String, Object>> localEmpsList = JSONUtils.toList(object);
		// 若项目组没有人
		if (localEmpsList.size() == 0)
		{
			projectTeamEmployee.updateProjectteamPersonById(bean);
			respon.put("header", "0000");
			respon.put("employees", "");
			log.info("out ProjectServiceImpl.updateProjectteamPersonById" + respon);
			return respon;
		}
		
		/*
		 * 本地项目组有人员数据的情况下，
		 * 找出有差异的地方。
		 */
		// 解析从hr系统获取的人员信息
		String hrEmpsJsonString = (String) bean.get("teamMember");
		JSONObject hrEmpsJsonObject = JSONObject.fromObject(hrEmpsJsonString);
		Object hrEmpsObject = hrEmpsJsonObject.get("employees");
		List<Map<String, Object>> hrEmpsList = JSONUtils.toList(hrEmpsObject);
		
		if (hrEmpsList.size() == 0)
		{
			respon.put("header", "0001");
			respon.put("employees", localEmpsList);
			log.info("out ProjectServiceImpl.updateProjectteamPersonById" + respon);
			return respon;
		}
		
		Map<String, Object> hrEmp;
		Map<String, Object> localEmp;
		List<Map<String, Object>> updateEmps = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < hrEmpsList.size(); i++)
		{
			hrEmp = hrEmpsList.get(i);
			updateEmps.add(hrEmp);
			for (int j = 0; j < localEmpsList.size(); j++)
			{
				localEmp = localEmpsList.get(j);
				if (hrEmp.get("eproNo").equals(localEmp.get("eproNo")))
				{
					localEmpsList.remove(j);
					break;
					
				}
				
			}
			
		}
		
		updateEmps.addAll(localEmpsList);
		
		JSONObject updatejson = new JSONObject();
		updatejson.put("employees", updateEmps);
		String jsonString = JSONUtils.toJSONString(updatejson);
		Map<String, Object> updatebean = new HashMap<String, Object>();
		
		updatebean.put("teamMember", jsonString);
		updatebean.put("projTeamId", bean.get("projTeamId"));
		if (localEmpsList.size() != 0)
		{
			
			projectTeamEmployee.updateProjectteamPersonById(updatebean);
			
			respon.put("header", "0001");
			respon.put("employees", localEmpsList);
			log.info("out ProjectServiceImpl.updateProjectteamPersonById" + respon);
			return respon;
		}
		
		projectTeamEmployee.updateProjectteamPersonById(updatebean);
		
		respon.put("header", "0000");
		respon.put("employees", "");
		log.info("out ProjectServiceImpl.updateProjectteamPersonById" + respon);
		return respon;
	}

	@Override
	public Map<String, Object> getProjectTeamEmployees(Map<String, Object> bean)
	{
		log.info("int ProjectServiceImpl.getProjectTeamEmployees" + bean);
		Map<String, Object> bean0 = new HashMap<String, Object>();
		Map<String, Object> bean1 = new HashMap<String, Object>();
		bean0.put("projTeamId", bean.get("projTeamId"));
		bean1.put("projectId", bean.get("projectId"));
		bean1.put("isLeave", 0);
		
		Map<String, Object> projectteamPerson = projectTeamEmployee.getProjectteamPersonById(bean0);
		List<Map<String, Object>> projectPersons = projectTeamEmployee.getProjectPersonByProjectIdOrEmpIdOrIsleave(bean1);
		
		
		// 解析json人员数据
		if (projectteamPerson == null || StringUtils.isEmpty(projectteamPerson.get("teamMember").toString())) 
		{
			Map<String, Object> respon = new HashMap<String, Object>();
			respon.put("header", "0001");
			respon.put("body", null);
			log.info("out ProjectServiceImpl.getProjectTeamEmployees" + respon);
			return respon;
		}
		String emps4json = projectteamPerson.get("teamMember").toString();
		JSONObject obj = JSONObject.fromObject(emps4json);
		Object object = obj.get("employees");
		List<Map<String, Object>> emps4projectteam = JSONUtils.toList(object);
		
		
		
		List<Map<String, Object>> returnEmps = new ArrayList<Map<String,Object>>();
		Map<String, Object> returnEmp;
		/**
		 * 
		 * 遍历核对
		 * 
		 */ 
		Map<String, Object> empsprojectteam;
		Map<String, Object> projectPerson;
		// 标记
		boolean flag0;
		for (int i = 0; i < emps4projectteam.size(); i++) 
		{
			returnEmp = new HashMap<String, Object>();
			flag0 = true;
			empsprojectteam = emps4projectteam.get(i);
			/*
			 * 拼接项目人员
			 */
			for (int j = 0; j < projectPersons.size(); j++)
			{
				projectPerson = projectPersons.get(j);
				if (empsprojectteam.get("eproNo").equals(projectPerson.get("employeeId")))
				{
					returnEmp.put("id", projectPerson.get("id"));
					returnEmp.put("jobNumber", empsprojectteam.get("eproNo"));
					returnEmp.put("name", empsprojectteam.get("name"));
					returnEmp.put("projectPO", projectPerson.get("projectPO"));
					returnEmp.put("status", 1);
					returnEmp.put("isCoreRole", "1".equals(projectPerson.get("isCoreRole").toString()) ? 1 : 0);
					returnEmp.put("planRole", projectPerson.get("planRole"));
					
					returnEmps.add(returnEmp);
					projectPersons.remove(j);
					flag0 = false;
					break;
				}
				
				
				
			}
			
			if (flag0)
			{
				returnEmp.put("id", -1);
				returnEmp.put("jobNumber", empsprojectteam.get("eproNo"));
				returnEmp.put("name", empsprojectteam.get("name"));
				returnEmp.put("projectPO", "");
				returnEmp.put("status", 0);
				returnEmp.put("isCoreRole", 0);
				returnEmps.add(returnEmp);
				returnEmp.put("planRole", 0);
			}
			
		}
		Map<String, Object> respon = new HashMap<String, Object>();
		respon.put("header", "0000");
		respon.put("body", returnEmps);
		
		log.info("out ProjectServiceImpl.getProjectTeamEmployees" + respon);
		return respon;
	}

	@Override
	public void addEmpsToProject(Map<String, Object> bean)
	{
		/*
		 * 解析数据
		 */
		log.info("in ProjectServiceImpl.addEmpsToProject" + bean);
		List<Map<String, Object>> persons = (List<Map<String, Object>>) bean.get("persons");
		String projTeamId = bean.get("projTeamId").toString();
		String projectId = bean.get("projectId").toString();
		String projectPO = bean.get("projectPO").toString();
		
		// 封装数据
		Map<String, Object> updatePerson = new HashMap<String, Object>();
		updatePerson.put("projectId", projectId);
		updatePerson.put("projTeamId", projTeamId);
		updatePerson.put("projectPO", projectPO);
		
		// 遍历操作进行数据库更新
		Map<String, Object> person;
		Map<String, Object> corePerson = new HashMap<String, Object>();
		for (int i = 0; i < persons.size(); i++)
		{
			person = persons.get(i);
			corePerson.put("projectId", projectId);
			corePerson.put("eproNo", person.get("eproNo"));
			// 选中的人员
			if ("1".equals(person.get("isSelect").toString()))
			{
				updatePerson.put("isLeave", 0);
				updatePerson.put("isCoreRole", person.get("isCoreRole"));
				updatePerson.put("employeeId", person.get("eproNo"));
				updatePerson.put("planRole", person.get("planRole"));
//				if (StringUtils.isNotEmpty(person.get("id").toString()))
				if (-1 != Integer.parseInt(person.get("id").toString()))
				{
					updatePerson.put("id", person.get("id"));
					projectTeamEmployee.updateProjectPerson(updatePerson);
				}
				else
				{
					updatePerson.put("personName", person.get("name"));
					
					Map<String, Object> updateIsLeave = new HashMap<String, Object>();
					updateIsLeave.put("employeeId", person.get("eproNo"));
					updateIsLeave.put("isLeave", 0);
					updateIsLeave.put("newIsLeave", 1);
					
					projectTeamEmployee.updateIsLeave(updateIsLeave);
					projectTeamEmployee.insertProjectPerson(updatePerson);
				}
				
				/*
				 * 核心人员信息更新
				 * 
				 */
				if ("1".equals(person.get("isCoreRole").toString()))
				{
					if (projectTeamEmployee.getCorePersonByProjectIdOrEproNo(corePerson).size() == 0)
					{
						corePerson.put("name", person.get("name"));
						projectTeamEmployee.insertCorePerson(corePerson);
					}
				}
				else
				{
					projectTeamEmployee.deleteCorePersonByProjectIdOrEproNo(corePerson);
				}
			}
			
			// 没选的人员
			// StringUtils.isNotEmpty(person.get("id").toString())
			else if (-1 != Integer.parseInt(person.get("id").toString()))
			{
				updatePerson.put("id", person.get("id"));
				updatePerson.put("isLeave", 1);
				projectTeamEmployee.updateProjectPerson(updatePerson);
				projectTeamEmployee.deleteCorePersonByProjectIdOrEproNo(corePerson);  // 删除曾经属于该项目的核心人员
			}
			
		}
		log.info("out ProjectServiceImpl.addEmpsToProject");
		
	}

	@Override
	public void delEmpFromProject(Map<String, Object> bean)
	{
		/*
		 * 
		 * 跟新项目人员表和核心人员表
		 * 
		 */
		log.info("in ProjectServiceImpl.delEmpFromProject" + bean);
		String eproNo = bean.get("jobNumber").toString();
		
		Map<String, Object> corePerson = new HashMap<String, Object>();
		corePerson.put("eproNo", eproNo);
		projectTeamEmployee.deleteCorePersonByProjectIdOrEproNo(corePerson);
		
		Map<String, Object> projectPerson = new HashMap<String, Object>();
		projectPerson.put("employeeId", eproNo);
		projectPerson.put("isLeave", 0);
		projectPerson.put("newIsLeave", 1);
		projectTeamEmployee.updateIsLeave(projectPerson);
		
		log.info("out ProjectServiceImpl.delEmpFromProject" + bean);
	}
	
	@Override
	public List<Map<String, Object>> delEmpsFromProject(Map<String, Object> bean)
	{
		log.info("in ProjectServiceImpl.delEmpsFromProject" + bean);
		String projectId = bean.get("projectId").toString();
		
		Map<String, Object> projectEmpBean = new HashMap<String, Object>();
		projectEmpBean.put("projectId", projectId);
		projectEmpBean.put("isLeave", 0);
		List<Map<String, Object>> delEmps = projectTeamEmployee.getProjectPersonByProjectIdOrEmpIdOrIsleave(projectEmpBean);
		/*
		 * 更新项目人员表和核心人员表
		 * 
		 */
		
		Map<String, Object> updateProjectPersons = new HashMap<String, Object>();
		updateProjectPersons.put("projectId", projectId);
		updateProjectPersons.put("isLeave", 0);
		updateProjectPersons.put("newIsLeave", 1);
		projectTeamEmployee.updateProjectPersons(updateProjectPersons);
		
		log.info("out ProjectServiceImpl.delEmpsFromProject" + delEmps);
		return delEmps;
	}

	/*
	 * 获取项目的人员信息
	 * (non-Javadoc)
	 * @see com.epro.pms.project_basic_info.service.ProjectService#getProjectEmps(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> getProjectEmps(Map<String, Object> bean)
	{
		log.info("out ProjectServiceImpl.getProjectEmps" + bean);
		// 查询还在项目做项目的人员
		bean.put("isLeave", 0);
		List<Map<String,Object>> empsAndCoreByProjectId = projectTeamEmployee.getEmpsAndCoreByProjectId(bean);
		
		// 规划角色转换成具体的文字形式
		EmpDic dic = EmpDic.getInstance();
		Map<String, Object> emp = null;
		String roletype = null;
		String role = null;
		for (int i = 0; i < empsAndCoreByProjectId.size(); i++)
		{
			emp = empsAndCoreByProjectId.get(i);
			roletype = emp.get("planRole").toString();
			role = dic.getRole(roletype);
			emp.put("planRole", role);
		}
		
		log.info("out ProjectServiceImpl.getProjectEmps" + empsAndCoreByProjectId);
		return empsAndCoreByProjectId;
	}

	@Override
	public List<Map<String, Object>> getContract4project(Map<String, Object> bean)
	{
		List<Map<String, Object>> contract4project = projectBasicInfoMapper.getContract4project(bean);
		
		return contract4project;
	}

}




















