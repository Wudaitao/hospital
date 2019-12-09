package com.hosptialsys.domain;

public class ExamItem {

	private Integer checkItemId;     //检查项目ID
	private String  userId;          //病人ID
	private String  checkItemName;   //检查项目名称
	private String  checkDate;       //检查该项目的日期
	private String  checkIsPaid;     //该检查项目是否已经付款
	private String  checkUserName;   //被检查的病人名字
	private String  checkResult;     //检查结果
	private String  checkItemContent;//检查内容
	
	public Integer getCheckItemId() {
		return checkItemId;
	}
	
	public void setCheckItemId(Integer checkItemId) {
		this.checkItemId = checkItemId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getCheckItemName() {
		return checkItemName;
	}
	
	public void setCheckItemName(String checkItemName) {
		this.checkItemName = checkItemName;
	}
	
	public String getCheckDate() {
		return checkDate;
	}
	
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	
	public String getCheckIsPaid() {
		return checkIsPaid;
	}
	
	public void setCheckIsPaid(String checkIsPaid) {
		this.checkIsPaid = checkIsPaid;
	}
	
	public String getCheckUserName() {
		return checkUserName;
	}
	
	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	
	public String getCheckResult() {
		return checkResult;
	}
	
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckItemContent() {
		return checkItemContent;
	}

	public void setCheckItemContent(String checkItemContent) {
		this.checkItemContent = checkItemContent;
	}
	
}
