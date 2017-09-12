package com.epro.pms.project_basic_info.dao;

import java.util.List;
import java.util.Map;

import com.epro.pms.project_basic_info.po.FpProject;
/**
 * 
 * @author Administrator
 *
 */
public interface FpProjectMapper
{
	/**
	 * 
	 * @param fpProject
	 */
	void insert(Map<String, Object> fpProject);
	/**
	 * 
	 * @param projectId
	 */
	void deleteFpProjectByPK(Integer projectId);
	/**
	 * 
	 * @param fpProject
	 */
	void updateFpProjectByPK(Map<String, Object> fpProject);
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	FpProject getFpProjectByPK(Integer projectId);
	/**
	 * 
	 * @return
	 */
	List<Map<String, Object>> getFpProject(Map<String, Object> condition);
	
	Map<String, Object> getContractInfo(Map<String, Object> condition);
	
	Map<String, Object> getFpProjectState(Map<String, Object> bean);
	
	Map<String, Object> getWorkloadByProjectId(Map<String, Object> bean);
}
