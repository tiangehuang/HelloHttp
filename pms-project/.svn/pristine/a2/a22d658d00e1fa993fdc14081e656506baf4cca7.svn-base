package com.epro.pms.project_basic_info.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epro.pms.project_basic_info.dao.ProjCorePersonInfoMapper;
import com.epro.pms.project_basic_info.po.ProjCorePersonInfo;
import com.epro.pms.project_basic_info.service.ProjCorePersonService;
/**
 * 
 * @author qinshiguang
 *
 */
@Service
public class ProjCorePersonServiceImpl implements ProjCorePersonService
{
	@Autowired
	private ProjCorePersonInfoMapper projCorePersonInfoMapper;
	
	@Override
	public void addProjCorePerson(Map<String, Object> projCorePersonInfo)
	{
		projCorePersonInfoMapper.insert(projCorePersonInfo);
		
	}

	@Override
	public void deleteProjCorePerson(Integer projectId)
	{
		projCorePersonInfoMapper.deleteProjCorePersonInfoByPK(projectId);
		
	}

	@Override
	public void updateProjCorePerson(Map<String, Object> projCorePersonInfo)
	{
		projCorePersonInfoMapper.updateProjCorePersonInfoByPK(projCorePersonInfo);
		
	}

	@Override
	public List<Map<String, Object>> getProjCorePersonByProjectId(Integer projectId)
	{
		List<Map<String, Object>> projCorePersonInfo = projCorePersonInfoMapper.getProjCorePersonInfoByProjectId(projectId);
		return projCorePersonInfo;
	}

	@Override
	public List<ProjCorePersonInfo> getProjCorePerson()
	{
		List<ProjCorePersonInfo> projCorePersonInfos = projCorePersonInfoMapper.getProjCorePersonInfo();
		return projCorePersonInfos;
	}

}
