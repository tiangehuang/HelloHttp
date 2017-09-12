package com.epro.pms.project_basic_info.service;

import java.util.List;
import java.util.Map;

public interface WeeklyloadService
{
	void insert(Map<String, Object> weeklyload) throws Exception;
	
	List<Map<String, Object>> getProjectsWeeklyloads(Map<String, Object> condition);
	
	Map<String, Object> getProjectWeeklyload(Map<String, Object> condition);
	
	void addPeriod(Map<String, Object> paramter) throws Exception;
	
	void savePeriod(Map<String, Object> paramter);
}
