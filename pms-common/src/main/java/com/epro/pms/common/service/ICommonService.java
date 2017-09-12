package com.epro.pms.common.service;

import java.util.List;
import java.util.Map;

import com.epro.pms.common.po.OptionPO;

public interface ICommonService {
	List<OptionPO> queryOptionList(Map<String, Object> paramMap);
	
	List<OptionPO> queryProLineOptionList(Map<String, Object> paramMap);
	
	List<OptionPO> queryPDUOptionList(Map<String, Object> paramMap);
	
	void saveNewProLineAndPdu(Map<String, Object> paramMap);
	
	String generateProjTeamNo(Map<String, Object> paramMap);
	
}
