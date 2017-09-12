/**
 * 
 */
package com.epro.pms.project_basic_info.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epro.pms.exception.CommonException;
import com.epro.pms.project_basic_info.dao.KeyRolesMatchBaselineMapper;
import com.epro.pms.project_basic_info.po.KeyRolesMatchBaselineInfo;
import com.epro.pms.project_basic_info.service.KeyRolesMatchBaseService;

/**
 * @author epro lvyalin
 *
 */
@Service
public class KeyRolesMatchBaseServiceImpl implements KeyRolesMatchBaseService{

	@Autowired
	KeyRolesMatchBaselineMapper keyRoleMapper;

	@Override
	public List<KeyRolesMatchBaselineInfo> findKeyRoleMatchBaseline(
			KeyRolesMatchBaselineInfo keyRoleInfo) throws CommonException {
		List<KeyRolesMatchBaselineInfo>  listInfo= keyRoleMapper.findKeyRoleMatchBaseline(keyRoleInfo);
		
		return listInfo;
	}
	
	@Override
	public int findProductLineCount(KeyRolesMatchBaselineInfo keyRolesInfo)throws CommonException{
		int total = keyRoleMapper.findProductLineCount(keyRolesInfo);
		return total;
	}

}
