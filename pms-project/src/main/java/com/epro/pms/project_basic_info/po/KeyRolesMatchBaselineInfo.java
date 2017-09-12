/**
 * 
 */
package com.epro.pms.project_basic_info.po;

import java.util.Date;

/**
 * @author epro
 *
 */
public class KeyRolesMatchBaselineInfo {

	public static final Float STANDARD = 0.3f; 
	
	private Integer employeeId;		//描述		唯一非空		默认		取值范围	

    private String name;			//姓名		非空

    private String area;			//地域
    
    private String projectPo;		//项目PO
    
    private Integer isCentralPerson;//是否骨干					0		1：是	 0：否

    private Integer isKeyRole;		//是否关键角色				0		1：是	 0：否

    private String projectName;		//所在项目名称				

    private String productLine;		//产品线					

    private String currentRole;		//当前角色			(1.TSE  2.PM  3.PL  4.TC  5.SE   6.MDE)

    private String planRole;		//规划角色					//默认当前角色

    private String hwPm;			//华为PM
    
    private Integer status;			//当前状态	0:在职 1：释放 2：离职
    
    private Integer isAnswerPassed;   //是否答辩通过:0-否；1-是
    
    private Integer tseCount; //1.TSE  2.PM  3.PL  4.TC  5.SE   6.MDE
    private Integer pmCount;
    private Integer plCount;
    private Integer tcCount;
    private Integer seCount;
    private Integer mdeCount;
    private Integer count;  //总人数
    private Integer keyroleAccount ;//关键角色占比	
    private Float matchAccount ;//匹配占比	
    private Float competentAccount ;//胜任占比
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getProjectPo() {
		return projectPo;
	}
	public void setProjectPo(String projectPo) {
		this.projectPo = projectPo;
	}
	public Integer getIsCentralPerson() {
		return isCentralPerson;
	}
	public void setIsCentralPerson(Integer isCentralPerson) {
		this.isCentralPerson = isCentralPerson;
	}
	public Integer getIsKeyRole() {
		return isKeyRole;
	}
	public void setIsKeyRole(Integer isKeyRole) {
		this.isKeyRole = isKeyRole;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProductLine() {
		return productLine;
	}
	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}
	public String getCurrentRole() {
		return currentRole;
	}
	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}
	public String getPlanRole() {
		return planRole;
	}
	public void setPlanRole(String planRole) {
		this.planRole = planRole;
	}
	public String getHwPm() {
		return hwPm;
	}
	public void setHwPm(String hwPm) {
		this.hwPm = hwPm;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsAnswerPassed() {
		return isAnswerPassed;
	}
	public void setIsAnswerPassed(Integer isAnswerPassed) {
		this.isAnswerPassed = isAnswerPassed;
	}
	public Integer getTseCount() {
		return tseCount;
	}
	public void setTseCount(Integer tseCount) {
		this.tseCount = tseCount;
	}
	public Integer getPmCount() {
		return pmCount;
	}
	public void setPmCount(Integer pmCount) {
		this.pmCount = pmCount;
	}
	public Integer getPlCount() {
		return plCount;
	}
	public void setPlCount(Integer plCount) {
		this.plCount = plCount;
	}
	public Integer getTcCount() {
		return tcCount;
	}
	public void setTcCount(Integer tcCount) {
		this.tcCount = tcCount;
	}
	public Integer getSeCount() {
		return seCount;
	}
	public void setSeCount(Integer seCount) {
		this.seCount = seCount;
	}
	public Integer getMdeCount() {
		return mdeCount;
	}
	public void setMdeCount(Integer mdeCount) {
		this.mdeCount = mdeCount;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getKeyroleAccount() {
		return keyroleAccount;
	}
	public void setKeyroleAccount(Integer keyroleAccount) {
		this.keyroleAccount = keyroleAccount;
	}
	public Float getMatchAccount() {
		return matchAccount;
	}
	public void setMatchAccount(Float matchAccount) {
		this.matchAccount = matchAccount;
	}
	public Float getCompetentAccount() {
		return competentAccount;
	}
	public void setCompetentAccount(Float competentAccount) {
		this.competentAccount = competentAccount;
	}
	@Override
	public String toString() {
		return "KeyRolesMatchBaselineInfo [employeeId=" + employeeId
				+ ", name=" + name + ", area=" + area + ", projectPo="
				+ projectPo + ", isCentralPerson=" + isCentralPerson
				+ ", isKeyRole=" + isKeyRole + ", projectName=" + projectName
				+ ", productLine=" + productLine + ", currentRole="
				+ currentRole + ", planRole=" + planRole + ", hwPm=" + hwPm
				+ ", status=" + status + ", isAnswerPassed=" + isAnswerPassed
				+ ", tseCount=" + tseCount + ", pmCount=" + pmCount
				+ ", plCount=" + plCount + ", tcCount=" + tcCount
				+ ", seCount=" + seCount + ", mdeCount=" + mdeCount
				+ ", count=" + count + ", keyroleAccount=" + keyroleAccount
				+ ", matchAccount=" + matchAccount + ", ompetentAccount="
				+ competentAccount + "]";
	}
    
	
    
    

	
}
