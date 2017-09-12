package com.epro.pms.project_basic_info.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

public interface WeeklyloadMapper
{
	void insertWeeklyload(Map<String, Object> weeklyload);
	
	void updateWeeklyloadById(Map<String, Object> weeklyload);
	
	List<Map<String, Object>> getWeeklyloads(Map<String, Object> condition);
	
	List<Map<String, Object>> getWeeklyloadByProjectIdOrPeriod(Map<String, Object> condition);
	
	void insertTimesheet(List<Map<String, Object>> timesheets);
	
	List<Map<String, Object>> getTimesheetsByProjectIdOrPeriod(Map<String, Object> condition);
	
	void updateTimesheetById(Map<String, Object> timesheet);
	
	Map<String, Object> getContractInfo(Map<String, Object> condition);
	
	List<Map<String, Object>> getTimesheetsByProjectIdAndEproNo(Map<String, Object> condition);
}
