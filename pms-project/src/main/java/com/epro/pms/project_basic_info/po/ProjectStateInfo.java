package com.epro.pms.project_basic_info.po;
/**
 * 项目状态信息
 * @author qinshiguang
 *
 */
public class ProjectStateInfo
{
	private Integer projectId;			//项目ID
	private Integer planWork;			//版本预估工作量
	private Integer currWork;			//自研投入工作量
	private String currStage;			//当前阶段
	private Integer qualiyDesc;			//总体质量评价
	
	private Integer schedule;			//进度
	private Integer frsQualiy;			//FRS评审质量
	private Integer codeQualiy;			//代码质量
	private Integer continuIntegration;	//持续集成
	private Integer commitTestQualiy;	//转测试质量
	
	private Integer testQualiy;			//测试质量
	private Integer projectAbility;		//工程能力
	private Integer processEvaluate;	//过程符合度评价
	private Integer qaWarnCount;		//QA预警次数
	private String qualiyAssess;		//质量点评
	public Integer getProjectId()
	{
		return projectId;
	}
	public void setProjectId(Integer projectId)
	{
		this.projectId = projectId;
	}
	public Integer getPlanWork()
	{
		return planWork;
	}
	public void setPlanWork(Integer planWork)
	{
		this.planWork = planWork;
	}
	public Integer getCurrWork()
	{
		return currWork;
	}
	public void setCurrWork(Integer currWork)
	{
		this.currWork = currWork;
	}
	public String getCurrStage()
	{
		return currStage;
	}
	public void setCurrStage(String currStage)
	{
		this.currStage = currStage;
	}
	public Integer getQualiyDesc()
	{
		return qualiyDesc;
	}
	public void setQualiyDesc(Integer qualiyDesc)
	{
		this.qualiyDesc = qualiyDesc;
	}
	public Integer getSchedule()
	{
		return schedule;
	}
	public void setSchedule(Integer schedule)
	{
		this.schedule = schedule;
	}
	public Integer getFrsQualiy()
	{
		return frsQualiy;
	}
	public void setFrsQualiy(Integer frsQualiy)
	{
		this.frsQualiy = frsQualiy;
	}
	public Integer getCodeQualiy()
	{
		return codeQualiy;
	}
	public void setCodeQualiy(Integer codeQualiy)
	{
		this.codeQualiy = codeQualiy;
	}
	public Integer getContinuIntegration()
	{
		return continuIntegration;
	}
	public void setContinuIntegration(Integer continuIntegration)
	{
		this.continuIntegration = continuIntegration;
	}
	public Integer getCommitTestQualiy()
	{
		return commitTestQualiy;
	}
	public void setCommitTestQualiy(Integer commitTestQualiy)
	{
		this.commitTestQualiy = commitTestQualiy;
	}
	public Integer getTestQualiy()
	{
		return testQualiy;
	}
	public void setTestQualiy(Integer testQualiy)
	{
		this.testQualiy = testQualiy;
	}
	public Integer getProjectAbility()
	{
		return projectAbility;
	}
	public void setProjectAbility(Integer projectAbility)
	{
		this.projectAbility = projectAbility;
	}
	public Integer getProcessEvaluate()
	{
		return processEvaluate;
	}
	public void setProcessEvaluate(Integer processEvaluate)
	{
		this.processEvaluate = processEvaluate;
	}
	public Integer getQaWarnCount()
	{
		return qaWarnCount;
	}
	public void setQaWarnCount(Integer qaWarnCount)
	{
		this.qaWarnCount = qaWarnCount;
	}
	public String getQualiyAssess()
	{
		return qualiyAssess;
	}
	public void setQualiyAssess(String qualiyAssess)
	{
		this.qualiyAssess = qualiyAssess;
	}
	@Override
	public String toString()
	{
		return "ProjectStateInfo [projectId=" + projectId + ", planWork=" + planWork + ", currWork=" + currWork
				+ ", currStage=" + currStage + ", qualiyDesc=" + qualiyDesc + ", schedule=" + schedule + ", frsQualiy="
				+ frsQualiy + ", codeQualiy=" + codeQualiy + ", continuIntegration=" + continuIntegration
				+ ", commitTestQualiy=" + commitTestQualiy + ", testQualiy=" + testQualiy + ", projectAbility="
				+ projectAbility + ", processEvaluate=" + processEvaluate + ", qaWarnCount=" + qaWarnCount
				+ ", qualiyAssess=" + qualiyAssess + "]";
	}
	
}
