package com.epro.pms.project_basic_info.service;

import java.util.List;
import java.util.Map;

import com.epro.pms.project_basic_info.po.RiskProject;
/**
 * 
 * @author qinshiguang
 *
 */
public interface RiskProjectService
{
	/**
	 * 
	 * @param riskProject
	 */
	void addRiskProject(Map<String, Object> riskProject);
	/**
	 * 
	 * @param projectId
	 */
	void deleteRiskProjectByPK(Integer projectId);
	/**
	 * 
	 * @param riskProject
	 */
	void updateRiskProjectByPK(Map<String, Object> riskProject);
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	RiskProject getRiskProjectByPK(Integer projectId);
	/**
	 * 
	 * @return
	 */
	List<Map<String, Object>> getRiskProject(Map<String, Object> page);
}
