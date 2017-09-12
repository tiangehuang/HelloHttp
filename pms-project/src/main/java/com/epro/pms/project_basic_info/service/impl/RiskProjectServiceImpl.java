package com.epro.pms.project_basic_info.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epro.pms.project_basic_info.dao.RiskProjectMapper;
import com.epro.pms.project_basic_info.po.RiskProject;
import com.epro.pms.project_basic_info.service.RiskProjectService;
@Service
public class RiskProjectServiceImpl implements RiskProjectService
{
	private static Log log = LogFactory.getLog(RiskProjectServiceImpl.class);
	
	@Autowired
	private RiskProjectMapper mapper;

	@Override
	public void addRiskProject(Map<String, Object> riskProject)
	{
		log.info("invoking..." + riskProject);
		mapper.insert(riskProject);
		
	}

	@Override
	public void deleteRiskProjectByPK(Integer projectId)
	{
		log.info("invoking..." + projectId);
		mapper.deleteRiskProjectByPK(projectId);
		
	}

	@Override
	public void updateRiskProjectByPK(Map<String, Object> riskProject)
	{
		log.info("invoking..." + riskProject);
		mapper.updateRiskProjectByPK(riskProject);
		
	}

	@Override
	public RiskProject getRiskProjectByPK(Integer projectId)
	{
		log.info("invoking..." + projectId);
		
		RiskProject riskProject = mapper.getRiskProjectByPK(projectId);
		
		return riskProject;
	}

	@Override
	public List<Map<String, Object>> getRiskProject(Map<String, Object> page)
	{
		log.info("invoking..." + page);
		
		List<Map<String, Object>> riskProjects = mapper.getRiskProject(page);

		return riskProjects;
	}

}
