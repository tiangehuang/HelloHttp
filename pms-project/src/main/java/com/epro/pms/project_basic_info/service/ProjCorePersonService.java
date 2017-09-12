package com.epro.pms.project_basic_info.service;

import java.util.List;
import java.util.Map;

import com.epro.pms.project_basic_info.po.ProjCorePersonInfo;
/**
 * 
 * @author qinshiguang
 *
 */
public interface ProjCorePersonService
{
	/**
	 * 
	 * @param projCorePersonInfo
	 */
	void addProjCorePerson(Map<String, Object> projCorePersonInfo);
	/**
	 * 
	 * @param projCorePersonInfo
	 */
	void deleteProjCorePerson(Integer projectId);
	/**
	 * 
	 * @param projCorePersonInfo
	 */
	void updateProjCorePerson(Map<String, Object> projCorePersonInfo);
	/**
	 * 
	 * @param projCorePersonInfo
	 * @return
	 */
	List<Map<String, Object>> getProjCorePersonByProjectId(Integer projectId);
	/**
	 * 
	 * @return
	 */
	List<ProjCorePersonInfo> getProjCorePerson();
}
