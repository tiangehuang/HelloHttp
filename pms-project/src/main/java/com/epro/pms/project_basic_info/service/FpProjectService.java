package com.epro.pms.project_basic_info.service;

import java.util.List;
import java.util.Map;

import com.epro.pms.project_basic_info.po.FpProject;
/**
 * 
 * @author qinshigaung
 *
 */
public interface FpProjectService
{
	/**
	 * 
	 * @param fpProject
	 */
	void addFpProject(Map<String,Object> fpProject);
	/**
	 * 
	 * @param projectId
	 */
	void deleteFpProjectByPK(Integer projectId);
	/**
	 * 
	 * @param fpProject
	 */
	void updateFpProject(Map<String, Object> fpProject);
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	FpProject getFpProjectByPK(Integer projectId);
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	List<Map<String, Object>> getFpProject(Map<String, Object> condition);
	
	Map<String, Object> getContractInfo(Map<String, Object> condition);
	
	Map<String, Object> getFpProjectState(Map<String, Object> bean);
	
	Map<String, Object> getWorkload(Map<String, Object> bean);
	
}
