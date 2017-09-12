/**
 * 
 */
package com.epro.pms.project_basic_info.po;

import java.util.Date;

/**
 * @author epro lvyalin
 *
 */
public class StatisticalFormInfo {
	
	private Integer mixedTypeTeam = 0;//1 混编型团队
	private Integer resourceTeam = 0;//2 资源型团队外包
	private Integer taskTeam = 0;//3 任务型团队外包
	private Integer openingTeam = 0;//4 开发型项目外包
	private Integer e2eTeam = 0;//5 E2E项目外包
	private Integer mixedTypeAccount =0;//混编型占比
	private Integer openingAccount = 0;//开发型占比
	//任务型占比
	private Integer taskAccount = 0;
	//资源型占比
	private Integer resourceAccount = 0;
	//E2E型占比
	private Integer projectAccount = 0; 
	private String productLine="";//产品线
	
	private String product="";//产品
	private String projectName ="";//项目名称
	private String area="";//地域
	private Integer projectPersonCount = 0;	//项目组人力总计
	private String projectMembersProblem=""; //人力情况分析
	//新增-->任春林
	private String ePmName;//易宝PM
	
	public Integer getMixedTypeTeam() {
		return mixedTypeTeam;
	}
	public void setMixedTypeTeam(Integer mixedTypeTeam) {
		this.mixedTypeTeam = mixedTypeTeam;
	}
	public Integer getResourceTeam() {
		return resourceTeam;
	}
	public void setResourceTeam(Integer resourceTeam) {
		this.resourceTeam = resourceTeam;
	}
	public Integer getTaskTeam() {
		return taskTeam;
	}
	public void setTaskTeam(Integer taskTeam) {
		this.taskTeam = taskTeam;
	}
	public Integer getOpeningTeam() {
		return openingTeam;
	}
	public void setOpeningTeam(Integer openingTeam) {
		this.openingTeam = openingTeam;
	}
	public Integer getE2eTeam() {
		return e2eTeam;
	}
	public void setE2eTeam(Integer e2eTeam) {
		this.e2eTeam = e2eTeam;
	}
	public Integer getOpeningAccount() {
		return openingAccount;
	}
	public void setOpeningAccount(Integer openingAccount) {
		this.openingAccount = openingAccount;
	}
	public Integer getTaskAccount() {
		return taskAccount;
	}
	public void setTaskAccount(Integer taskAccount) {
		this.taskAccount = taskAccount;
	}
	public Integer getResourceAccount() {
		return resourceAccount;
	}
	public void setResourceAccount(Integer resourceAccount) {
		this.resourceAccount = resourceAccount;
	}
	public Integer getProjectAccount() {
		return projectAccount;
	}
	public void setProjectAccount(Integer projectAccount) {
		this.projectAccount = projectAccount;
	}
	public String getProductLine() {
		return productLine;
	}
	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getProjectPersonCount() {
		return projectPersonCount;
	}
	public void setProjectPersonCount(Integer projectPersonCount) {
		this.projectPersonCount = projectPersonCount;
	}
	public String getProjectMembersProblem() {
		return projectMembersProblem;
	}
	public void setProjectMembersProblem(String projectMembersProblem) {
		this.projectMembersProblem = projectMembersProblem;
	}
	public String getePmName() {
		return ePmName;
	}
	public void setePmName(String ePmName) {
		this.ePmName = ePmName;
	}
	public Integer getMixedTypeAccount() {
		return mixedTypeAccount;
	}
	public void setMixedTypeAccount(Integer mixedTypeAccount) {
		this.mixedTypeAccount = mixedTypeAccount;
	}

	public String toString() {
		return "StatisticalFormInfo [mixedTypeTeam=" + mixedTypeTeam
				+ ", resourceTeam=" + resourceTeam + ", taskTeam=" + taskTeam
				+ ", openingTeam=" + openingTeam + ", e2eTeam=" + e2eTeam
				+ ", mixedTypeAccount=" + mixedTypeAccount
				+ ", openingAccount=" + openingAccount + ", taskAccount="
				+ taskAccount + ", resourceAccount=" + resourceAccount
				+ ", projectAccount=" + projectAccount + ", productLine="
				+ productLine + ", product=" + product + ", projectName="
				+ projectName + ", area=" + area + ", projectPersonCount="
				+ projectPersonCount + ", projectMembersProblem="
				+ projectMembersProblem + ", ePmName=" + ePmName + "]";
	}

	
	
}
