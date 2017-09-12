package com.epro.pms.project_basic_info.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.epro.pms.exception.CommonException;
import com.epro.pms.project_basic_info.po.ProjectBasicInfo;
import com.epro.pms.project_basic_info.po.StatisticalFormInfo;

@Service
public interface StatisticalFormService{

	/**
	 * @return List<ProjectBasicInfo> info
	 */
	List<StatisticalFormInfo>  listStatisticalForm(StatisticalFormInfo statisticalFormInfo)throws CommonException;
	
	List<Map<String, Object>> getAreaData()throws CommonException;
	
}
