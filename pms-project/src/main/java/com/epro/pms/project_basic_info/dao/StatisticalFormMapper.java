package com.epro.pms.project_basic_info.dao;

import java.util.List;
import java.util.Map;

import com.epro.pms.project_basic_info.po.StatisticalFormInfo;
/**
 * 
 * @author lvyalin
 *
 */
public interface StatisticalFormMapper
{
	/**
	 * @return List<Map<String, Object>> info
	 */
	List<StatisticalFormInfo> listStatisticalForm(StatisticalFormInfo projectInfo);
	
	List<Map<String, Object>> getAreaData();
	
	
}
