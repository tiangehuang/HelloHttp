package com.epro.pms.project_basic_info.dao;

import java.util.List;
import java.util.Map;

import com.epro.pms.project_basic_info.po.ProjectBasicInfo;
import com.epro.pms.util.Page;
/**
 * 
 * @author qinshiguang
 *
 */
public interface ProjectBasicInfoMapper
{
	/**
	 * 
	 * @param projdecId
	 * @return project basic info 
	 */
	ProjectBasicInfo getProjectBasicInfoByPK(Integer projdecId);
	/**
	 * 
	 * @return
	 */
	List<Map<String, Object>> getProjectBasicInfo(Map<String, Object> condition);
	/**
	 * 
	 * @param projdecId
	 */
	void deleteProjectBasicInfoByPK(Integer projdecId);
	/**
	 * 
	 * @param projectBasicInfo
	 */
	void insert(Map<String, Object> projectBasicInfo);
	/**
	 * 
	 * @param projectBasicInfo
	 */
	void updateProjectBasicInfoByPK(Map<String, Object> projectBasicInfo);
	/**
	 * @desc 删除项目状态，相当于软删除
	 * @param projectId
	 */
	void softDelProjectByPK(Integer projectId);
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	Map<String, Object> getBasic(Map<String, Object> project);
	
	void updateContract(Map<String, Object> contract);
	
	Map<String, Object> getContractByPK(Integer contractId);
	
	List<Map<String, Object>> getProjectState(Map<String, Object> condition);
	
	 /**
	  * @author epro
	  * @param paramMap 条件筛选参数
	  * @return 返回符合条件的结果
	  */
	 List<Map<String, Object>> getContract4project(Map<String, Object> paramMap);
}
