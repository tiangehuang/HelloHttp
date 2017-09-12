package com.epro.pms.project_basic_info.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface WorkWeeklyService {
	//获取项目及周报信息
	List<Map<String,Object>> getProjectANDWeekloads(Map<String,Object> condition);
	//查询人员及周期
	Map<String,Object> getPersonAndPeriod (Map<String,Object> condition)throws ParseException;
	//保存周报信息
	List<Map<String,Object>> saveAllMessages(Map<String,Object> condition);
	//根据projectId和period获取周报信息
	List<Map<String,Object>> getWeeksByPeriod(Map<String,Object> condition);
	//根据项目名称导出周报
	boolean downLoadWeeklyReport(String path,Map<String,Object> condition);
	//根据项目组名称获取项目
	List<Map<String,Object>> getProjectName(Map<String,Object> condition);
	//根据Id获取项目名称
	String getProjectNameById(String id);
	
	void deleteWeekly(Map<String,Object> condition);
}
