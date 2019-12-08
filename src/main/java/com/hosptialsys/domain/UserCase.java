package com.hosptialsys.domain;

public class UserCase {
	
	private Integer caseId;         //病历ID
	private String  userId;         //病人ID
	private String  caseResult;     //病人此次检查的结果
	private Integer caseIsFinish;   //这次病历是否完成
	private String  caseDate;       //此次病历的日期
	
	public Integer getCaseId() {
		return caseId;
	}
	
	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getCaseResult() {
		return caseResult;
	}
	
	public void setCaseResult(String caseResult) {
		this.caseResult = caseResult;
	}
	
	public Integer getCaseIsFinish() {
		return caseIsFinish;
	}
	
	public void setCaseIsFinish(Integer caseIsFinish) {
		this.caseIsFinish = caseIsFinish;
	}

	public String getCaseDate() {
		return caseDate;
	}

	public void setCaseDate(String caseDate) {
		this.caseDate = caseDate;
	}
	
}
