package com.epro.pms.project_basic_info.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epro.pms.project_basic_info.dao.WeeklyloadMapper;
import com.epro.pms.project_basic_info.service.WeeklyloadService;
/**
 * @desc 周报管理服务，对参数进行提取封装处理
 * @author epro
 *
 */
@Service
public class WeeklyloadServiceImpl implements WeeklyloadService
{
	private static Log log = LogFactory.getLog(WeeklyloadServiceImpl.class);
	
	@Autowired
	private WeeklyloadMapper mapper;
	/**
	 * @desc 周报添加数据方法，对两个表进行操作，添加数据
	 * @author epro
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void insert(Map<String, Object> weeklyload) throws Exception
	{
		Map<String, Object> contractCondition = new HashMap<String, Object>();
		contractCondition.put("contractId", weeklyload.get("contractId"));
		Map<String, Object> contractInfo = mapper.getContractInfo(contractCondition);
		
		weeklyload.put("projectName", contractInfo.get("projectName"));
		weeklyload.put("projTeamName", contractInfo.get("projTeamName"));
		weeklyload.put("poNo", contractInfo.get("poNo"));
		weeklyload.put("start", contractInfo.get("pStartTime"));
		weeklyload.put("end", contractInfo.get("pEndTime"));
		weeklyload.put("wAllWorkload", 0);
		
		Integer projectId = Integer.valueOf(weeklyload.get("projectId").toString());
		String projTeamName = (String) weeklyload.get("projTeamName");
		// 个人的周报容器的公共数据部分
		List<Map<String, Object>> timesheets = new ArrayList<Map<String, Object>>();
		Map<String, Object> timesheet = new HashMap<String, Object>();
		timesheet.put("projectId", projectId);
		timesheet.put("projTeamName", projTeamName);
		timesheet.put("wCapacity", 0);
//		timesheet.put("weekWorkload", 0);
		/**
		 * 
		 * 筛选人员
		 * 
		 */
		List<Map<String, Object>> originalEmps = (List<Map<String, Object>>) weeklyload.get("employees");
		List<Map<String, Object>> emps = new ArrayList<Map<String,Object>>();
		Map<String, Object> isExistEmpCondition = new HashMap<String, Object>();
		isExistEmpCondition.put("projectId", projectId);
		List<Map<String,Object>> returnList;
		for (int i = 0; i < originalEmps.size(); i++)
		{   
			isExistEmpCondition.put("eproNo", originalEmps.get(i).get("eproNo"));
			returnList = null;
			returnList = mapper.getTimesheetsByProjectIdAndEproNo(isExistEmpCondition);
			
			if (returnList.size() == 0)
			{
				emps.add(originalEmps.get(i));
			}
		}
		if (emps.size() == 0)
		{
			log.info("没有新增人员...");
			return;
		}
		
		
		
		
		/*
		 * 获取项目的开始和结束日期
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = (Date) weeklyload.get("start");
		Date endDate = (Date) weeklyload.get("end");
		
		
		/*
		 * 转换成caleder对象
		 */
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		startTime.setTime(startDate);
		endTime.setTime(endDate);
		
		/*
		 * 判断是否为项目启动后添加人员
		 */
		Map<String, Object> weeklyloadCondition = new HashMap<String, Object>();
		weeklyloadCondition.put("projectId", projectId);
		List<Map<String, Object>> weeklyloadByProjectId = mapper.getWeeklyloadByProjectIdOrPeriod(weeklyloadCondition);
		boolean isAddAfter = true;
		// 判断是否已经添加过周报
		if (weeklyloadByProjectId.size() != 0)
		{
			isAddAfter = false;
			Date todayDate = new Date();
			Calendar todayCal = Calendar.getInstance();
			todayCal.setTime(todayDate);
			// 判断是否为项目中途添加人员
			if (todayCal.compareTo(startTime) > 0)
			{
				startTime.setTime(todayDate);
			}
		}
		
		/*
		 * 划分周期
		 */
		if (endTime.compareTo(startTime) <= 0)
		{
			throw new Exception("日期取值范围异常，start：" + sdf.format(startTime.getTime()) + "end：" + sdf.format(endTime.getTime()));
		}
		int dayPlus = 0;
		String period = null;
		int empsLen = emps.size();
		int isContainSunday = 0;
		while (endTime.compareTo(startTime) >= 0)
		{
			timesheets.clear();
			// 获取当前日期的星期几
			int dayOfWeek = startTime.get(Calendar.DAY_OF_WEEK);
			// 获取一周的最后一天，也就是星期六
			Calendar saturday = (Calendar) startTime.clone();
			Calendar sunday = (Calendar) startTime.clone();
			dayPlus = dayOfWeek == 7 ? 0 : 7 - dayOfWeek;
			saturday.add(Calendar.DAY_OF_WEEK, dayPlus);
			
			sunday.add(Calendar.DAY_OF_WEEK, 1 - startTime.get(Calendar.DAY_OF_WEEK));
//			isContainSunday = startTime.compareTo(sunday) == 0 ? 1 : 0;
			
			period = (startTime.get(Calendar.YEAR) * 100  + startTime.get(Calendar.WEEK_OF_YEAR)) + "";
			weeklyload.put("startTime", sdf.format(startTime.getTime()));
			weeklyload.put("period", period);
			
			timesheet.put("wStartTime", sdf.format(startTime.getTime()));
			timesheet.put("period", period);
			
			if (endTime.compareTo(saturday) > 0)
			{
				
				timesheet.put("wEndTime", sdf.format(saturday.getTime()));
//				timesheet.put("weekWorkload", saturday.get(Calendar.DAY_OF_YEAR) - startTime.get(Calendar.DAY_OF_YEAR) - isContainSunday);
				
				for (int i = 0; i < empsLen; i++)
				{
					Map<String, Object> ts = new HashMap<String, Object>();
					ts.putAll(timesheet);
					ts.put("eproNo", emps.get(i).get("eproNo"));
					ts.put("name", emps.get(i).get("name"));
					timesheets.add(ts);
				}
				
				weeklyload.put("endTime", sdf.format(saturday.getTime()));
				if (isAddAfter)
				{
					mapper.insertWeeklyload(weeklyload);
				}
				mapper.insertTimesheet(timesheets);
				
				// 以周日作为一周的第一天
				startTime.add(Calendar.DAY_OF_WEEK, dayOfWeek == 1 ? 7 : 8 - dayOfWeek);
				
			}
			else if (endTime.compareTo(saturday) == 0)
			{
				timesheet.put("wEndTime", sdf.format(saturday.getTime()));
				timesheet.put("weekWorkload", saturday.get(Calendar.DAY_OF_YEAR) - startTime.get(Calendar.DAY_OF_YEAR) - isContainSunday);
				for (int i = 0; i < empsLen; i++)
				{
					Map<String, Object> ts = new HashMap<String, Object>();
					ts.putAll(timesheet);
					ts.put("eproNo", emps.get(i).get("eproNo"));
					ts.put("name", emps.get(i).get("name"));
					timesheets.add(ts);
				}
				
				weeklyload.put("endTime", sdf.format(saturday.getTime()));
				if (isAddAfter)
				{
					mapper.insertWeeklyload(weeklyload);
				}
				mapper.insertTimesheet(timesheets);
				break;
			}
			else 
			{
				timesheet.put("wEndTime", sdf.format(endTime.getTime()));
				timesheet.put("weekWorkload", endTime.get(Calendar.DAY_OF_YEAR) - startTime.get(Calendar.DAY_OF_YEAR) - isContainSunday + 1);
				for (int i = 0; i < empsLen; i++)
				{
					Map<String, Object> ts = new HashMap<String, Object>();
					ts.putAll(timesheet);
					ts.put("eproNo", emps.get(i).get("eproNo"));
					ts.put("name", emps.get(i).get("name"));
					timesheets.add(ts);
				}
				
				weeklyload.put("endTime", sdf.format(endTime.getTime()));
				if (isAddAfter)
				{
					mapper.insertWeeklyload(weeklyload);
				}
				mapper.insertTimesheet(timesheets);
				break;
			}
			
		}
		
	}
	/**
	 * @desc 周报首页请求方法或者搜索周报
	 * 
	 */
	@Override
	public List<Map<String, Object>> getProjectsWeeklyloads(Map<String, Object> condition)
	{
		List<Map<String, Object>> weeklyloadReports = new ArrayList<Map<String, Object>>();
		Map<String, Object> weeklyloadReport = new HashMap<String, Object>();
		
		/*
		 * 
		 * 默认获取正在工作中的项目上一周周期的数据
		 * 
		 */
		
		List<Map<String, Object>> weeklyloads;
		String curPeriod = null;
		String projectName = (String) condition.get("projectName");
		if (StringUtils.isEmpty(projectName))
		{
			Calendar now = Calendar.getInstance();
			now.setTimeInMillis(System.currentTimeMillis());
			curPeriod = now.get(Calendar.YEAR) * 100 + now.get(Calendar.WEEK_OF_YEAR) - 1 + "";
			
			condition.put("period", curPeriod);
			
			weeklyloads = mapper.getWeeklyloads(condition);
			
			List<Map<String, Object>> add = new ArrayList<Map<String,Object>>();
			condition.remove("period");
			int size = weeklyloads.size();
			for (int i = 0; i < size; i++)
			{
				add.clear();
				condition.put("projectName", weeklyloads.get(0).get("projectName"));
				add = mapper.getWeeklyloads(condition);
				weeklyloads.addAll(add);
				
				weeklyloads.remove(0);
				
			}
			condition.remove("projectName");
			
		}
		else
		{
			// 请求每个项目的所有周报信息
			weeklyloads = mapper.getWeeklyloads(condition);
		}
		
		/*
		 * 获取每个项目的所有人周报情况
		 */
		Integer preProjectId = -1;
		Integer currProjectId = 0;
		int fromIndex = 0;
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 0; i < weeklyloads.size(); i++)
		{
			currProjectId = (Integer) weeklyloads.get(i).get("projectId");
			
			if (preProjectId.equals(currProjectId) && i != (weeklyloads.size()-1))
			{
				preProjectId = currProjectId;
				continue;
			}
			param.clear();
			param.put("period", StringUtils.isEmpty(projectName) ? curPeriod : weeklyloads.get(i).get("period"));
			param.put("projectId", weeklyloads.get(i).get("projectId"));
			// 请求项目的人员周工作量信息
			if (i != 0)
			{
				if (i == weeklyloads.size() - 1)
				{
					weeklyloadReport.put("weeklyload", weeklyloads.subList(fromIndex, i + 1));
					weeklyloadReports.add(weeklyloadReport);
				}
				else
				{
					// 周报
					weeklyloadReport.put("weeklyload", weeklyloads.subList(fromIndex, i));
					weeklyloadReports.add(weeklyloadReport);
					weeklyloadReport = new HashMap<String, Object>();
					fromIndex = i;
				}
			}
			
			// 员工
			List<Map<String, Object>> emps = mapper.getTimesheetsByProjectIdOrPeriod(param);
			weeklyloadReport.put("emps", emps);
			
			
			preProjectId = currProjectId;
		}
		preProjectId = (Integer) weeklyloads.get(weeklyloads.size()-2).get("projectId");
		if (!preProjectId.equals(currProjectId))
		{
			weeklyloadReport.put("weeklyload", weeklyloads.subList(weeklyloads.size()-1, weeklyloads.size()));
			weeklyloadReports.add(weeklyloadReport);
		}
		
		
		return weeklyloadReports;
	}

	@Override
	public Map<String, Object> getProjectWeeklyload(Map<String, Object> condition)
	{
		Map<String, Object> weeklyloadReport = new HashMap<String, Object>();
		
		List<Map<String, Object>> weeklyloads = mapper.getWeeklyloadByProjectIdOrPeriod(condition);
		List<Map<String, Object>> timesheets = mapper.getTimesheetsByProjectIdOrPeriod(condition);
		
		weeklyloadReport.put("weeklyload", weeklyloads.get(0));
		weeklyloadReport.put("timesheets", timesheets);
		
		return weeklyloadReport;
	}

	@Override
	public void addPeriod(Map<String, Object> paramter) throws ParseException
	{
		/**
		 * 项目周报的处理
		 */
		/*
		 * 获取某个项目的所有周报信息
		 * 并且获取最后一个周期的周报信息
		 */
		List<Map<String, Object>> weeklyloads = mapper.getWeeklyloadByProjectIdOrPeriod(paramter);
		Map<String, Object> lastOne = weeklyloads.get(weeklyloads.size() - 1);
		
		// 创建新增的周报对象
		Map<String, Object> addOne = new HashMap<String, Object>();
		addOne.put("projectId", lastOne.get("projectId"));
		addOne.put("projectName", lastOne.get("projectName"));
		addOne.put("poNo", lastOne.get("poNo"));
		addOne.put("projTeamName", lastOne.get("projTeamName"));
		addOne.put("period", (Integer.valueOf(lastOne.get("period").toString())) + 1);
		addOne.put("wAllWorkload", 0);
		
		Date endDate = (Date) lastOne.get("endTime");
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(endDate);
		
		for (int i = 0; i < 7; i++)
		{
			if (endTime.get(Calendar.DAY_OF_WEEK) != 7)
			{
				endTime.add(Calendar.DAY_OF_YEAR, 1);
			}
			else 
			{
				lastOne.put("endTime", endTime.getTime());
				mapper.updateWeeklyloadById(lastOne);
				
				endTime.add(Calendar.DAY_OF_YEAR, 1);
				addOne.put("startTime", endTime.getTime());
			}
		}
		
		addOne.put("endTime", endTime.getTime());
		mapper.insertWeeklyload(addOne);
		
		/**
		 * 人员信息的处理
		 */
		Map<String, Object> cond = new HashMap<String, Object>();
		cond.put("projectId", lastOne.get("projectId"));
		cond.put("period", lastOne.get("period"));
		
		List<Map<String, Object>> emps = mapper.getTimesheetsByProjectIdOrPeriod(cond);
		
		List<Map<String, Object>> newEmps = new ArrayList<Map<String,Object>>();
		Map<String, Object> newEmp = null;
		Map<String, Object> emp = null;
		for (int i = 0; i < emps.size(); i++)
		{
			emp = emps.get(i);
			newEmp = new HashMap<String, Object>();
			newEmp.put("projectId", emp.get("projectId"));
			newEmp.put("projTeamName", emp.get("projTeamName"));
			newEmp.put("period", (Integer.valueOf(emp.get("period").toString())) + 1);
			newEmp.put("eproNo", emp.get("eproNo"));
			newEmp.put("name", emp.get("name"));
			newEmp.put("wCapacity", 0);
			
			Date endDat = (Date) emp.get("wEndTime");
			Calendar endCa = Calendar.getInstance();
			endCa.setTime(endDat);
			
			for (int j = 0; j < 7; j++)
			{
				if (endCa.get(Calendar.DAY_OF_WEEK) != 7)
				{
					endCa.add(Calendar.DAY_OF_YEAR, 1);
				}
				else
				{
					emp.put("wEndTime", endCa.getTime());
					mapper.updateTimesheetById(emp);
					
					endCa.add(Calendar.DAY_OF_YEAR, 1);
					newEmp.put("wStartTime", endCa.getTime());
				}
			}
			
			newEmp.put("wEndTime", endCa.getTime());
			newEmps.add(newEmp);
		}
		
		mapper.insertTimesheet(newEmps);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void savePeriod(Map<String, Object> paramter)
	{
		log.info("Service: WeeklyloadServiceImpl.savePeriod() is invoked. param=" + paramter);
		
		Map<String, Object> weeklyload = (Map<String, Object>) paramter.get("weeklyload");
		List<Map<String, Object>> emps = (List<Map<String, Object>>) paramter.get("emps");
		Map<String, Object> emp = new HashMap<String, Object>();
		int wAllWorkload = 0;
		for (int i = 0; i < emps.size(); i++)
		{
			emp = emps.get(i);
			wAllWorkload += Integer.valueOf(emp.get("weekWorkload").toString());
			mapper.updateTimesheetById(emp);
		}
		
		weeklyload.put("wAllWorkload", wAllWorkload);
		mapper.updateWeeklyloadById(weeklyload);
		
		log.info("Service: WeeklyloadServiceImpl.savePeriod() is returned.");
		
	}

}























