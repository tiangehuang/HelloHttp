package com.epro.pms.project_basic_info.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epro.pms.project_basic_info.dao.IProjEmployeeMapper;
import com.epro.pms.project_basic_info.po.ProjectBasicInfo;
import com.epro.pms.project_basic_info.po.EmpDic;
import com.epro.pms.project_basic_info.service.IProjEmployeeService;
import com.epro.pms.util.AESUtils;
import com.epro.pms.util.CommonUtils;
import com.epro.pms.util.HttpUtils;
import com.epro.pms.util.PropertiesUtils;

@Service
public class ProjEmployeeServiceImpl implements IProjEmployeeService {
	private static Log log = LogFactory.getLog(ProjEmployeeServiceImpl.class);
	
	@Autowired
	private IProjEmployeeMapper proEmployeeMapper;

	@Override
	public Map<String, Object> queryEmployeeListByKeyword(
			Map<String, Object> paramMap) {
		log.info("Service: PorjEmployeeServiceImpl.queryEmployeeListByKeyword() is invoked. param=" + paramMap);
		// step1. 封装请求参数
		JSONObject reqObj = new JSONObject();
		JSONObject headerObj = new JSONObject();
		//	header
		headerObj.put("userid", "");
		headerObj.put("token", "");
		headerObj.put("beginnum", paramMap.get("beginnum"));
		headerObj.put("endnum", paramMap.get("endnum"));
		headerObj.put("systype", "");
		reqObj.put("header", headerObj);
		//  request
		paramMap.remove("beginnum");
		paramMap.remove("endnum");
		reqObj.put("request", paramMap);
		
		// step2. 请求接口
		String rspTxt = HttpUtils.postJson(PropertiesUtils.getProperty("url.indistinct"), reqObj.toString());
		log.info("----未解密响应字符串----:" + rspTxt);
		
		// step3. 解密并转对象
		String decryptedTxt = AESUtils.decrypt(rspTxt);
		log.info("----解密后响应字符串----:" + decryptedTxt);
		JSONObject rspObj = JSONObject.fromObject(decryptedTxt);
		log.info("----解析后的对象----:" + rspObj.toString());
		
		
		/*
		 * 通过数据字典转换
		 */
		JSONObject rspResponse = (JSONObject) rspObj.get("response");
		List<Map<String, Object>> employees = (List<Map<String, Object>>) rspResponse.get("employees");
		Map<String, Object> employee = null;
		EmpDic skill = EmpDic.getInstance();
		String tmp = null;
		for (int i = 0; i < employees.size(); i++) {
			employee = employees.get(i);
			tmp = (String) employee.get("skill");
			employee.put("skill", skill.getSkill(tmp));
			
		}
		
		// step4. 判断请求结果
		JSONObject rspHeader = (JSONObject) rspObj.get("header");
		String rspcode = (String) rspHeader.get("rspcode");
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("rspcode", rspcode);
		if ("0000".equals(rspcode)) {
			resMap.put("rspdesc", (String) rspHeader.get("rspdesc"));
			resMap.put("response", rspObj.get("response"));
		}
		
		log.info("Service: PorjEmployeeServiceImpl.queryEmployeeListByKeyword() is returned. result=" + resMap);
		return resMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryEmployeeListByConditions(
			Map<String, Object> paramMap) {
		log.info("Service: PorjEmployeeServiceImpl.queryEmployeeListByConditions() is invoked. param=" + paramMap);
		// step1. 封装请求参数
		JSONObject reqObj = new JSONObject();
		JSONObject headerObj = new JSONObject();
		//	header
		headerObj.put("userid", "");
		headerObj.put("token", "");
		headerObj.put("beginnum", paramMap.get("beginnum"));
		headerObj.put("endnum", paramMap.get("endnum"));
		headerObj.put("systype", "");
		reqObj.put("header", headerObj);
		//  request
		paramMap.remove("beginnum");
		paramMap.remove("endnum");
		reqObj.put("request", paramMap);
		
		// step2. 请求接口
		String rspTxt = HttpUtils.postJson(PropertiesUtils.getProperty("url.exactness"), reqObj.toString());
		log.info("----未解密响应字符串----:" + rspTxt);
		
		// step3. 解密并转对象
		String decryptedTxt = AESUtils.decrypt(rspTxt);
		log.info("----解密后响应字符串----:" + decryptedTxt);
		JSONObject rspObj = JSONObject.fromObject(decryptedTxt);
		log.info("----解析后的对象----:" + rspObj.toString());
		
		/*
		 * 通过数据字典转换
		 */
		/*JSONObject rspResponse = (JSONObject) rspObj.get("response");
		List<Map<String, Object>> employees = (List<Map<String, Object>>) rspResponse.get("employees");
		Map<String, Object> employee = null;
		EmpDic skill = EmpDic.getInstance();
		String tmp = null;
		for (int i = 0; i < employees.size(); i++) {
			employee = employees.get(i);
			tmp = (String) employee.get("skill");
			employee.put("skill", skill.getSkill(tmp));
			
		}*/
		
		// step4. 判断请求结果
		JSONObject rspHeader = (JSONObject) rspObj.get("header");
		String rspcode = (String) rspHeader.get("rspcode");
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("rspcode", rspcode);
		if ("0000".equals(rspcode)) {
			resMap.put("rspdesc", (String) rspHeader.get("rspdesc"));
			resMap.put("response", rspObj.get("response"));
		}
		
		log.info("Service: PorjEmployeeServiceImpl.queryEmployeeListByConditions() is returned. result=" + resMap);
		return resMap;
	}

	@Override
	public Map<String, Object> queryAssociationList(Map<String, Object> paramMap) {
		log.info("Service: PorjEmployeeServiceImpl.queryAssociationList() is invoked. param=" + paramMap);
		// step1. 封装请求参数
		JSONObject reqObj = new JSONObject();
		JSONObject headerObj = new JSONObject();
		//	header
		headerObj.put("userid", "");
		headerObj.put("token", "");
		headerObj.put("beginnum", 1);
		headerObj.put("endnum", 10);
		headerObj.put("systype", "");
		reqObj.put("header", headerObj);
		//  request
		reqObj.put("request", paramMap);
		
		// step2. 请求接口
		String rspTxt = HttpUtils.postJson(PropertiesUtils.getProperty("url.association"), reqObj.toString());
		log.info("----未解密响应字符串----:" + rspTxt);
		
		// step3. 解密并转对象
		String decryptedTxt = AESUtils.decrypt(rspTxt);
		log.info("----解密后响应字符串----:" + decryptedTxt);
		JSONObject rspObj = JSONObject.fromObject(decryptedTxt);
		log.info("----解析后的对象----:" + rspObj.toString());
		
		// step4. 判断请求结果
		JSONObject rspHeader = (JSONObject) rspObj.get("header");
		String rspcode = (String) rspHeader.get("rspcode");
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("rspcode", rspcode);
		if ("0000".equals(rspcode)) {
			resMap.put("rspdesc", (String) rspHeader.get("rspdesc"));
			resMap.put("response", rspObj.get("response"));
		}
		
		log.info("Service: PorjEmployeeServiceImpl.queryAssociationList() is returned. result=" + resMap);
		return resMap;
	}

	@Override
	public boolean addEmployeesToProject(
			Map<String, Object> paramMap) {
		log.info("Service: PorjEmployeeServiceImpl.addEmployeesToProject() is invoked. param=" + paramMap);
		// 1.查询项目信息
		String projectId = (String) paramMap.get("projectId");
		log.info("----查询项目信息----: projectId=" + projectId);
		ProjectBasicInfo projInfo = proEmployeeMapper.queryProjInfoById(projectId);
		log.info("----查询项目信息----: result=" + projInfo);
		
		List<Map<String, Object>> employees = (List<Map<String, Object>>) paramMap.get("employees");
		
		List<Map<String, Object>> coreEmployees = new ArrayList<Map<String, Object>>();
		
		// 2.更新hr系统
		boolean flag = true;
		Map<String, Object> corePerson = new HashMap<String, Object>();
		for (int i = 0; i < employees.size(); i++) {
			log.info("-----更新第" + i + "个员工.");
			Map<String, Object> employee = employees.get(i);
			// 1.拼字符串
			JSONObject reqObj = new JSONObject();
			JSONObject headerObj = new JSONObject();
			JSONObject bodyObj = new JSONObject();
			//	header
			headerObj.put("userid", "");
			headerObj.put("token", "");
			headerObj.put("beginnum", 0);
			headerObj.put("endnum", 0);
			headerObj.put("systype", "");
			reqObj.put("header", headerObj);
			//  request
			bodyObj.put("jobnumber", CommonUtils.convertNull(employee.get("jobNumber")));
			bodyObj.put("personstate", "在职");
			bodyObj.put("projectteam", CommonUtils.convertNull(projInfo.getProjTeamName()));
			String isCore = CommonUtils.convertNull(employee.get("isbackone"));
			bodyObj.put("isbackbone", ("1".equals(isCore) ? "是" : "否") );
			bodyObj.put("corptype", CommonUtils.convertNull(projInfo.getCorpType()));
			bodyObj.put("corppattern", CommonUtils.convertNull(projInfo.getCorpMode()));
			bodyObj.put("productline", CommonUtils.convertNull(projInfo.getProductLine()));
//			bodyObj.put("jobrank", CommonUtils.convertNull(employee.get("planRole")));
			bodyObj.put("role", CommonUtils.convertNull(employee.get("planRole")));
			bodyObj.put("isonshore", ""); //CommonUtils.convertNull(employee.get("isOnshore"))
			bodyObj.put("pduorspdt", CommonUtils.convertNull(projInfo.getProduct()));
			bodyObj.put("location", CommonUtils.convertNull(projInfo.getArea()));
			bodyObj.put("projectmanager", CommonUtils.convertNull(projInfo.getePmName()));
			bodyObj.put("deptmanager", CommonUtils.convertNull(projInfo.getDeptManager()));
			
			reqObj.put("request", bodyObj);
			// 2. 请求接口
			String rspTxt = HttpUtils.postJson(PropertiesUtils.getProperty("url.updation"), reqObj.toString());
			log.info("----未解密响应字符串----:" + rspTxt);
			
			// 3. 解密并转对象
			String decryptedTxt = AESUtils.decrypt(rspTxt);
			log.info("----解密后响应字符串----:" + decryptedTxt);
			JSONObject rspObj = JSONObject.fromObject(decryptedTxt);
			log.info("----解析后的对象----:" + rspObj.toString());
			
			// step4. 判断请求结果
			JSONObject rspHeader = (JSONObject) rspObj.get("header");
			String rspcode = (String) rspHeader.get("rspcode");
			
			if (!"0000".equals(rspcode)) {
				flag = false;
				break;
			}
			
			// step5. 判断是否核心骨干
			/*if ("1".equals(isCore)) {
				corePerson.put("projectId", projInfo.getProjectId());
				corePerson.put("eproNo", CommonUtils.convertNull(employee.get("jobNumber")));
				corePerson.put("name", CommonUtils.convertNull(employee.get("personName")));
				Map<String, Object> empByEproNo = proEmployeeMapper.getCoreEmployeeByEproNo(corePerson);
				
				 * 骨干人员插入分析：
				 * 		1. 根据eproNo 查询有数据
				 * 				|--- 1. 项目ID相同 （不做处理）
				 * 				|--- 2. 项目ID不相同 （insert）
				 * 		2. 根据eproNo 查询无数 （insert）
				 * 
				 * 
				 
				if (empByEproNo == null || empByEproNo != null && !projectId.equals(empByEproNo.get("projectId").toString())) 
				{
					proEmployeeMapper.delectCoreEmployeeByEproNo(corePerson);
					proEmployeeMapper.insertCoreEmployee(corePerson);
				} 
			} else {
				corePerson.put("eproNo", CommonUtils.convertNull(employee.get("jobNumber")));
				proEmployeeMapper.delectCoreEmployeeByEproNo(corePerson);
			}*/
		}
		log.info("是否所有员工更新成功：" + flag);
		
		// step6.
		/*if (coreEmployees.isEmpty()) {
			log.info("-----骨干员工为空----");
		} else {
			log.info("-----插入骨干员工列表---- param=" + coreEmployees);
			proEmployeeMapper.insertCoreEmployees(coreEmployees);
		}*/
		
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteEmployeeFromProject(Map<String, Object> paramMap)
	{
		log.info("Service: PorjEmployeeServiceImpl.deleteEmployeeFromProject() is invoked. param=" + paramMap);
		
		Map<String, Object> employee = (Map<String, Object>) paramMap.get("employee");
		
		
		// 2.更新hr系统
		boolean flag = true;
		// 1.拼字符串
		JSONObject reqObj = new JSONObject();
		JSONObject headerObj = new JSONObject();
		JSONObject bodyObj = new JSONObject();
		//	header
		headerObj.put("userid", "");
		headerObj.put("token", "");
		headerObj.put("beginnum", 0);
		headerObj.put("endnum", 0);
		headerObj.put("systype", "");
		reqObj.put("header", headerObj);
		//  request
		bodyObj.put("jobnumber", CommonUtils.convertNull(employee.get("jobNumber")));
		bodyObj.put("personstate", "闲置");
		bodyObj.put("projectteam", "");
		bodyObj.put("isonshore", ""); 
		bodyObj.put("pduorspdt", "");
		bodyObj.put("projectmanager", "");
		bodyObj.put("deptmanager", "");
		
		reqObj.put("request", bodyObj);
		// 2. 请求接口
		String rspTxt = HttpUtils.postJson(PropertiesUtils.getProperty("url.updation"), reqObj.toString());
		log.info("----未解密响应字符串----:" + rspTxt);
		
		// 3. 解密并转对象
		String decryptedTxt = AESUtils.decrypt(rspTxt);
		log.info("----解密后响应字符串----:" + decryptedTxt);
		JSONObject rspObj = JSONObject.fromObject(decryptedTxt);
		log.info("----解析后的对象----:" + rspObj.toString());
		
		// step4. 判断请求结果
		JSONObject rspHeader = (JSONObject) rspObj.get("header");
		String rspcode = (String) rspHeader.get("rspcode");
		
		if (!"0000".equals(rspcode)) {
			flag = false;
			return flag;
		}
		
		/*
		 * 
		 * 删除核心人员信息
		 * 
		 */
		Map<String, Object> empDele = new HashMap<String, Object>();
		empDele.put("eproNo", CommonUtils.convertNull(employee.get("jobNumber")));
		proEmployeeMapper.delectCoreEmployeeByEproNo(empDele);
		
		return true;
	}
	
}
