package com.epro.pms.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.epro.pms.common.po.OptionPO;

@Repository
public interface ICommonMapper {
	List<OptionPO> queryProLineOptionList(Map<String, Object> paramMap);
	
	List<OptionPO> queryPDUOptionList(Map<String, Object> paramMap);
	
	boolean isProLineAndPduExists(Map<String, Object> paramMap);
	
	void insertProLineAndPdu(Map<String, Object> paramMap);
	
	String queryProjTeamMaxNo(Map<String, Object> paramMap);
	
	//查询项目组编号是否存在
	String queryProjTeamNo(String ProjTeamNo);
}
