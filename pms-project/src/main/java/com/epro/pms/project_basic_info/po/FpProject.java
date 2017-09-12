package com.epro.pms.project_basic_info.po;

import java.util.Date;
/**
 * 
 * @author qinshiguang
 *
 */
public class FpProject
{
	private Integer projectId;			//项目ID
	private String projectName;			//项目名称
	private Date transitionStartTime;	//计划转型开始时间
	private Date transitionEndTime;		//计划转型结束时间
	private Integer bAmount;			//项目工作量
	
	private String plalProgress;		//项目预期完成进度（%）
	private String currProgress;		//项目实际完成进度（%）
	private String plalCost;			//计划投入成本(人天）
	private String currCost;			//实际投入成本（人天）
	private String riskIssues;			//风险问题内容
	
	private String poStatusRisk;		//PO看护状态及风险
	private String area;				//地域
	private String productLine;			//产品线
	public Integer getProjectId()
	{
		return projectId;
	}
	public void setProjectId(Integer projectId)
	{
		this.projectId = projectId;
	}
	public String getProjectName()
	{
		return projectName;
	}
	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}
	public Date getTransitionStartTime()
	{
		return transitionStartTime;
	}
	public void setTransitionStartTime(Date transitionStartTime)
	{
		this.transitionStartTime = transitionStartTime;
	}
	public Date getTransitionEndTime()
	{
		return transitionEndTime;
	}
	public void setTransitionEndTime(Date transitionEndTime)
	{
		this.transitionEndTime = transitionEndTime;
	}
	public Integer getbAmount()
	{
		return bAmount;
	}
	public void setbAmount(Integer bAmount)
	{
		this.bAmount = bAmount;
	}
	public String getPlalProgress()
	{
		return plalProgress;
	}
	public void setPlalProgress(String plalProgress)
	{
		this.plalProgress = plalProgress;
	}
	public String getCurrProgress()
	{
		return currProgress;
	}
	public void setCurrProgress(String currProgress)
	{
		this.currProgress = currProgress;
	}
	public String getPlalCost()
	{
		return plalCost;
	}
	public void setPlalCost(String plalCost)
	{
		this.plalCost = plalCost;
	}
	public String getCurrCost()
	{
		return currCost;
	}
	public void setCurrCost(String currCost)
	{
		this.currCost = currCost;
	}
	public String getRiskIssues()
	{
		return riskIssues;
	}
	public void setRiskIssues(String riskIssues)
	{
		this.riskIssues = riskIssues;
	}
	public String getPoStatusRisk()
	{
		return poStatusRisk;
	}
	public void setPoStatusRisk(String poStatusRisk)
	{
		this.poStatusRisk = poStatusRisk;
	}
	public String getArea()
	{
		return area;
	}
	public void setArea(String area)
	{
		this.area = area;
	}
	public String getProductLine()
	{
		return productLine;
	}
	public void setProductLine(String productLine)
	{
		this.productLine = productLine;
	}
	@Override
	public String toString()
	{
		return "FpProject [projectId=" + projectId + ", projectName=" + projectName + ", transitionStartTime="
				+ transitionStartTime + ", transitionEndTime=" + transitionEndTime + ", bAmount=" + bAmount
				+ ", plalProgress=" + plalProgress + ", currProgress=" + currProgress + ", plalCost=" + plalCost
				+ ", currCost=" + currCost + ", riskIssues=" + riskIssues + ", poStatusRisk=" + poStatusRisk + ", area="
				+ area + ", productLine=" + productLine + "]";
	}
}
