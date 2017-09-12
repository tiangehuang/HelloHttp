package com.epro.pms.project_basic_info.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.epro.pms.project_basic_info.po.ProjectBasicInfo;

@Repository
public interface IProjEmployeeMapper {
	
	public ProjectBasicInfo queryProjInfoById(String projectId);
	
	void insertCoreEmployees(List<Map<String, Object>> coreEmployees);
	
	void delectCoreEmployeeByEproNo(Map<String, Object> coreEmployee);
	
	void insertCoreEmployee(Map<String, Object> coreEmployee);
	
	Map<String, Object> getCoreEmployeeByEproNo(Map<String, Object> condition);
}
