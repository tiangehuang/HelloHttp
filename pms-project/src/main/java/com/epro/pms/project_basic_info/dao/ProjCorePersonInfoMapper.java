package com.epro.pms.project_basic_info.dao;

import java.util.List;
import java.util.Map;

import com.epro.pms.project_basic_info.po.ProjCorePersonInfo;
/**
 * qinshiguang
 * @author Administrator
 *
 */
public interface ProjCorePersonInfoMapper
{
	/**
	 * 
	 * @param projCorePersonInfo
	 */
	void insert(Map<String, Object> projCorePersonInfo);
	/**
	 * 
	 * @param projectId
	 */
	void deleteProjCorePersonInfoByPK(Integer projectId);
	/**
	 * 
	 * @param projectId
	 */
	void updateProjCorePersonInfoByPK(Map<String, Object> projCorePersonInfo);
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	List<Map<String, Object>> getProjCorePersonInfoByProjectId(Integer projectId);
	/**
	 * 
	 * @return
	 */
	List<ProjCorePersonInfo> getProjCorePersonInfo();
}
