package com.epro.pms.project_basic_info.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epro.pms.project_basic_info.dao.ProjectTimesheetMapper;
import com.epro.pms.project_basic_info.service.ProjectTimesheetService;
@Service
public class ProjectTimesheetServiceImpl implements ProjectTimesheetService
{
	@Autowired
	private ProjectTimesheetMapper mapper;

	@Override
	public void add(Map<String, Object> timesheet)
	{
		mapper.insert(timesheet);
		
	}

	@Override
	public List<Map<String, Object>> get(Map<String, Object> condition)
	{
		List<Map<String, Object>> timesheet = mapper.get(condition);
		return timesheet;
	}

}
