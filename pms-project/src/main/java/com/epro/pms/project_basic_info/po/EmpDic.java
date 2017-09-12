package com.epro.pms.project_basic_info.po;

import java.util.HashMap;
import java.util.Map;


public class EmpDic
{
	private static EmpDic instance;
	
	private Map<String, String> SKILL = new HashMap<String, String>();
	private Map<String, String> POSITION = new HashMap<String, String>();
	private Map<String, String> ROLE = new HashMap<String, String>();
	
	private EmpDic()
	{
		initSkill();
		initPosition();
		initRole();
	}
	
	public static EmpDic getInstance()
	{
		if (instance == null)
		{
			instance = new EmpDic();
		}
		
		return instance;
	}
	
	/*
	 * <select name="skill"> 
	 * 	<option value="A0101">JAVA工程师 </option> 
	 * 	<option value="A0102">android工程师 </option> 
	 * 	<option value="A0103">C++工程 </option> 
	 * 	<option value="A0104">IOS工程师 </option> 
	 * 	<option value="A0105">HTML工程师 </option> 
	 * 	<option value="A0106">数据库开发工程师 </option> 
	 * 	<option value="A0107">前端开发工程师 </option> 
	 * 	<option value="A0108">资料工程师 </option> 
	 * 	<option value="A0201">软件测试工程师 </option> 
	 * 	<option value="A0202">硬件测试工程师 </option> 
	 * 	<option value="A0301">运营工程师 </option> 
	 * 	<option value="A0302">维护工程师 </option> 
	 * 	<option value="A0401">UI </option> 
	 * 	<option value="A0402">UE </option> 
	 * 	<option value="A0501">质量工程师 </option> 
	 * 	<option value="A0601">系统分析 </option> 
	 * 	<option value="A0602">架构设计 </option> 
	 * 	<option value="B0101">行政 </option> 
	 * 	<option value="B0102">前台 </option> 
	 * 	<option value="B0201">招聘专员 </option> 
	 * 	<option value="B0202">员工关系专员 </option> 
	 * 	<option value="B0203">培训专员 </option> 
	 * 	<option value="B0204">薪酬绩效专员 </option> 
	 * 	<option value="B0301">出纳 </option> 
	 * 	<option value="B0302">会计 </option> 
	 * 	<option value="B0401">信息安全专员 </option> 
	 * 	<option value="B0402">IT网管 </option> 
	 * 	<option value="C0001">总经理 </option> 
	 * 	<option value="C0002">总监 </option> 
	 * 	<option value="C0003">部门经理 </option> 
	 * 	<option value="C0004">项目经理 </option> 
	 * 	<option value="C0005">业务经理 </option> 
	 * 	<option value="C0006">业务助理 </option>
	 * </select>
	 */
	private void initSkill()
	{
		SKILL.put("A0101", "JAVA工程师 ");
		SKILL.put("A0102", "android工程师 ");
		SKILL.put("A0103", "C++工程师 ");
		SKILL.put("A0104", "IOS工程师 ");
		SKILL.put("A0105", "HTML工程师 ");
		SKILL.put("A0106", "数据库开发工程师 ");
		SKILL.put("A0107", "前端开发工程师 ");
		SKILL.put("A0108", "资料工程师 ");
		SKILL.put("A0201", "软件测试工程师 ");
		SKILL.put("A0202", "硬件测试工程师 ");
		SKILL.put("A0301", "运营工程师 ");
		SKILL.put("A0302", "维护工程师 ");
		
		SKILL.put("A0401", "UI");
		SKILL.put("A0402", "UE ");
		SKILL.put("A0501", "质量工程师 ");
		SKILL.put("A0601", "系统分析 ");
		SKILL.put("A0602", "架构设计 ");
		SKILL.put("B0101", "行政 ");
		SKILL.put("B0102", "前台");
		SKILL.put("B0201", "招聘专员");
		SKILL.put("B0202", "员工关系专员 ");
		SKILL.put("B0203", "培训专员 ");
		
		SKILL.put("B0204", "薪酬绩效专员");
		SKILL.put("B0301", "出纳");
		SKILL.put("B0302", "会计 ");
		SKILL.put("B0401", "信息安全专员 ");
		SKILL.put("B0402", "IT网管 ");
		SKILL.put("C0001", "总经理 ");
		SKILL.put("C0002", "总监 ");
		SKILL.put("C0003", "部门经理 ");
		SKILL.put("C0004", "项目经理");
		SKILL.put("C0005", "业务经理");
		SKILL.put("C0006", "业务助理");
	}
	/*
	 * <select name="position"> 
	 * 	<option value="A01">开发类 </option> 
	 * 	<option value="A02">测试类 </option> 
	 * 	<option value="A03">运营类 </option> 
	 * 	<option value="A04">视觉类 </option> 
	 * 	<option value="A05">质量类 </option>
	 *  <option value="A06">系统类 </option>
	 *  <option value="B01">行政类 </option>
	 *  <option value="B02">人事类 </option>
	 *  <option value="B03">财务类 </option> 
	 *  <option value="B04">IT类 </option> 
	 *  <option value="C00">管理类 </option>
	 * </select>
	 */
	private void initPosition()
	{
		POSITION.put("A01", "开发类");
		POSITION.put("A02", "测试类");
		POSITION.put("A03", "运营类");
		POSITION.put("A04", "视觉类");
		POSITION.put("A05", "质量类");
		POSITION.put("A06", "系统类");
		POSITION.put("B01", "行政类");
		POSITION.put("B02", "行政类");
		POSITION.put("B03", "财务类");
		POSITION.put("B04", "IT类");
		POSITION.put("C00", "管理类");
	}
	/*
	 * 1.JAVA
	 * 2.Android
	 * 3.IOS
	 * 4.HTML
	 * 5.前端开发
	 * 6.数据库开发
	 * 7.C++
	 * 8.资料
	 * 9.软件测试
	 * 10.硬件测试
	 * 11.运维工程师
	 * 12.UI
	 * 13.UCD
	 * 14.QA
	 * 15.SE
	 * 16.BA
	 * 17.SA
	 * 18.CIE
	 * 19.MDE
	 * 20.PM
	 * 21.TC
	 * 22.TSE
	 * 23.其他
	 */
	public void initRole()
	{
		ROLE.put("0", "");
		ROLE.put("1", "JAVA");
		ROLE.put("2", "Android");
		ROLE.put("3", "IOS");
//		ROLE.put("4", "HTML");
		ROLE.put("4", "前端开发");
		ROLE.put("5", "数据库开发");
		ROLE.put("6", "C++");
		ROLE.put("7", "资料");
		ROLE.put("8", "软件测试");
		ROLE.put("9", "硬件测试");
		ROLE.put("10", "运维工程师");
		ROLE.put("11", "UI");
		ROLE.put("12", "UCD");
		ROLE.put("13", "QA");
		ROLE.put("14", "SE");
		ROLE.put("15", "BA");
		ROLE.put("16", "SA");
		ROLE.put("17", "CIE");
		ROLE.put("18", "MDE");
		ROLE.put("19", "PM");
		ROLE.put("20", "TC");
		ROLE.put("21", "TSE");
		ROLE.put("22", "PL");
		ROLE.put("23", "其他");
	}
	
	public String getSkill(String key)
	{
		return SKILL.get(key);
	}
	
	public String getPosition(String key)
	{
		return POSITION.get(key);
	}
	
	public String getRole(String key)
	{
		return ROLE.get(key);
	}
}


























