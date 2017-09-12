package com.epro.pms.project_basic_info.service;

import java.util.List;
import java.util.Map;

import com.epro.pms.exception.CommonException;
import com.epro.pms.project_basic_info.po.ProjectBasicInfo;
import com.epro.pms.util.Page;
/**
 * 
 * @author qinshiguang
 *
 */
public interface ProjectService
{
	/**
	 * 
	 * @param projectBasicInfo
	 * @throws CommonException
	 */
	void addProject(Map<String, Object> projectBasicInfo) throws CommonException;
	/**
	 * 
	 * @param projectId
	 * @throws CommonException
	 */
	void deleteProject(Integer projectId) throws CommonException;
	/**
	 * 
	 * @param projectBasicInfo
	 * @throws CommonException
	 */
	void updateProject(Map<String, Object> projectBasicInfo) throws CommonException;
	/**
	 * 
	 * @param projectId
	 * @return
	 * @throws CommonException
	 */
	ProjectBasicInfo getProjectByPK(Integer projectId) throws CommonException;
	/**
	 * 
	 * @return
	 * @throws CommonException
	 */
	List<Map<String, Object>> getProjects(Map<String, Object> condition) throws CommonException;
	/**
	 * 软删除
	 * @param projectId
	 */
	void softDelProjectByPK(Integer projectId);
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	Map<String, Object> getBasic(Map<String, Object> project);
	
	List<Map<String, Object>> getProjectEmps(Map<String, Object> bean);
	
	void updateContract(Map<String, Object> contract);
	
	Map<String, Object> getContractByPK(Integer contractId);
	
	List<Map<String, Object>> getContract4project(Map<String, Object> bean);
	
	List<Map<String, Object>> getProjectState(Map<String, Object> condition);
	
	
	/**
	 * 
	 * 项目组---人员---项目信息服务维护
	 * 
	 */
	Map<String, Object> updateProjectteamPersonById(Map<String, Object> bean);
	void hardUpdateProjectteamPersonById(Map<String, Object> bean);
	
	Map<String, Object> getProjectTeamEmployees(Map<String, Object> bean);
	
	void addEmpsToProject(Map<String, Object> bean);
	
	void delEmpFromProject(Map<String, Object> bean);
	
	List<Map<String, Object>> delEmpsFromProject(Map<String, Object> bean);
	
}
