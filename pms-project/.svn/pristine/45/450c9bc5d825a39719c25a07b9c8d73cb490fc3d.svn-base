package com.epro.pms.project_basic_info.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epro.pms.exception.CommonException;
import com.epro.pms.project_basic_info.po.KeyRolesMatchBaselineInfo;

@Service
public interface KeyRolesMatchBaseService{

	/**
	 * @return List<KeyRolesMatchBaselineInfo> info
	 */
	List<KeyRolesMatchBaselineInfo>  findKeyRoleMatchBaseline(KeyRolesMatchBaselineInfo keyRoleInfo)throws CommonException;
	
	int findProductLineCount(KeyRolesMatchBaselineInfo keyRolesInfo) throws CommonException;
	
}
