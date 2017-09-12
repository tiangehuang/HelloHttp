package com.epro.pms.project_basic_info.dao;

import java.util.List;
import java.util.Map;

public interface WorkWeeklyMapper {
	//获取所有项目信息
	List<Map<String, Object>> getProjects(Map<String, Object> condition);
	//获取所有周报信息
	List<Map<String,Object>> getProjectWeekloads(Map<String,Object> condition);
	//获取项目的所有周期
	List<Map<String,Object>> getProjectPeriod(Map<String,Object> condition);
	//查询项目现有员工
	List<Map<String,Object>> getPersonByProjectId(Integer projectId);
	//添加项目周期
	void insertPeriod(Map<String,Object> condition);
	//添加周报信息
	void insertTimesheet(List<Map<String,Object>> condition);
	//根据ID更新周报数据
	void updateTimesheetById(Map<String,Object> condition);
	//获取根据项目组名称获取项目名称
	List<Map<String,Object>> getProjecNameByProjTeam(Map<String,Object> condition);
	//根据ID获取项目名称
	String getProjNameById(String id);
	
	void deleteWeekly(Map<String,Object> condition);
}
