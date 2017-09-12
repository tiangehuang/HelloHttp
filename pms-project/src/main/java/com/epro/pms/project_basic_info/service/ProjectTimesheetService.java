package com.epro.pms.project_basic_info.service;

import java.util.List;
import java.util.Map;

public interface ProjectTimesheetService
{
	void add(Map<String, Object> timesheet);
	
	List<Map<String, Object>> get(Map<String, Object> condition);
}
