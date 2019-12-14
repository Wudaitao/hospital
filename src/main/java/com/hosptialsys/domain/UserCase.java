package com.hosptialsys.domain;

public class UserCase {
	
	private Integer caseId;         //病历ID
	private String  userId;         //病人ID
	private String  casePerfance;   //临床表现
	private String  caseFirst;      //初步诊断
	private String  caseResult;     //病人此次看病的结果
	private Integer caseIsFinish;   //这次病历是否完成
	private String  caseDate;       //此次病历的日期
	private String  caseDoctorId;   //看病医生ID
	
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

	public String getCasePerfance() {
		return casePerfance;
	}

	public void setCasePerfance(String casePerfance) {
		this.casePerfance = casePerfance;
	}

	public String getCaseFirst() {
		return caseFirst;
	}

	public void setCaseFirst(String caseFirst) {
		this.caseFirst = caseFirst;
	}

	public String getCaseDoctorId() {
		return caseDoctorId;
	}

	public void setCaseDoctorId(String caseDoctorId) {
		this.caseDoctorId = caseDoctorId;
	}
	
	
}
