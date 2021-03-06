package com.epro.pms.project_basic_info.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epro.pms.project_basic_info.dao.FpProjectMapper;
import com.epro.pms.project_basic_info.po.FpProject;
import com.epro.pms.project_basic_info.service.FpProjectService;
/**
 * 
 * @author qinshiguang
 *
 */
@Service
public class FpProjectServiceImpl implements FpProjectService
{
	@Autowired
	private FpProjectMapper fpProjectMapper;

	@Override
	public void deleteFpProjectByPK(Integer projectId)
	{
		fpProjectMapper.deleteFpProjectByPK(projectId);
		
	}
	@Override
	public void updateFpProject(Map<String, Object> fpProject)
	{
		
		fpProjectMapper.updateFpProjectByPK(fpProject);
		
	}

	@Override
	public FpProject getFpProjectByPK(Integer projectId)
	{
		FpProject fpProject = fpProjectMapper.getFpProjectByPK(projectId);
		
		return fpProject;
	}

	@Override
	public List<Map<String, Object>> getFpProject(Map<String, Object> condition)
	{
		List<Map<String, Object>> fpProject = fpProjectMapper.getFpProject(condition);
		
		return fpProject;
	}
	@Override
	public void addFpProject(Map<String, Object> fpProject)
	{
		fpProjectMapper.insert(fpProject);
		
	}
	@Override
	public Map<String, Object> getContractInfo(Map<String, Object> condition)
	{
		Map<String, Object> contractInfo = fpProjectMapper.getContractInfo(condition);
		return contractInfo;
	}
	@Override
	public Map<String, Object> getFpProjectState(Map<String, Object> bean)
	{
		Map<String, Object> state = fpProjectMapper.getFpProjectState(bean);
		return state;
	}
	@Override
	public Map<String, Object> getWorkload(Map<String, Object> bean)
	{
		Map<String, Object> workload = fpProjectMapper.getWorkloadByProjectId(bean);
		return workload;
	}
	
	

}
