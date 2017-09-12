package com.epro.pms.project_basic_info.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epro.pms.project_basic_info.dao.ProjectStateInfoMapper;
import com.epro.pms.project_basic_info.po.ProjectStateInfo;
import com.epro.pms.project_basic_info.service.ProjStateService;
/**
 * 
 * @author qinshiguang
 *
 */
@Service
public class ProjStateServiceImpl implements ProjStateService
{
	@Autowired
	private ProjectStateInfoMapper ProjectStateInfoMapper;

	@Override
	public void addProjState(Map<String, Object> projectStateInfo)
	{
		ProjectStateInfoMapper.insert(projectStateInfo);
		
	}

	@Override
	public void deleteProjStateByPK(Integer projectId)
	{
		ProjectStateInfoMapper.deleteProjectStateInfoByPK(projectId);
		
	}

	@Override
	public void updateProjStateByPK(ProjectStateInfo projectStateInfo)
	{
		ProjectStateInfoMapper.updateProjectStateInfoByPK(projectStateInfo);
		
	}

	@Override
	public ProjectStateInfo getProjectStateByPK(Integer projectId)
	{
		ProjectStateInfo projectStateInfo = ProjectStateInfoMapper.getProjectStateInfoByPK(projectId);
		return projectStateInfo;
	}

	@Override
	public List<ProjectStateInfo> getProjectState()
	{
		List<ProjectStateInfo> projectStateInfos = ProjectStateInfoMapper.getProjectStateInfo();
		return projectStateInfos;
	}

}
