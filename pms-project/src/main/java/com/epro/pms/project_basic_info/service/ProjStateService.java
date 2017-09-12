package com.epro.pms.project_basic_info.service;

import java.util.List;
import java.util.Map;

import com.epro.pms.project_basic_info.po.ProjectStateInfo;
/**
 * 
 * @author qinshiguang
 *
 */
public interface ProjStateService
{
	/**
	 * 
	 * @param projectStateInfo
	 */
	void addProjState(Map<String, Object> projectStateInfo);
	/**
	 * 
	 * @param projectStateInfo
	 */
	void deleteProjStateByPK(Integer projectId);
	/**
	 * 
	 * @param projectStateInfo
	 */
	void updateProjStateByPK(ProjectStateInfo projectStateInfo);
	/**
	 * 
	 * @param projectStateInfo
	 * @return
	 */
	ProjectStateInfo getProjectStateByPK(Integer projectId);
	/**
	 * 
	 * @return
	 */
	List<ProjectStateInfo> getProjectState();
}
