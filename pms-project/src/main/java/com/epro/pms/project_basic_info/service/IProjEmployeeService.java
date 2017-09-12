package com.epro.pms.project_basic_info.service;

import java.util.List;
import java.util.Map;

public interface IProjEmployeeService {
	Map<String, Object> queryEmployeeListByKeyword(Map<String, Object> paraMap);
	
	Map<String, Object> queryEmployeeListByConditions(Map<String, Object> paramMap);
	
	Map<String, Object> queryAssociationList(Map<String, Object> paramMap);
	
	boolean addEmployeesToProject(Map<String, Object> paramMap);
	
	boolean deleteEmployeeFromProject(Map<String, Object> paramMap);
	
}
