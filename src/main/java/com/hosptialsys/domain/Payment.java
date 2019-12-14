package com.hosptialsys.domain;

public class Payment {
	private Integer paymentId;		//付款条目的ID，主键
	private String workerId;		//收款人的ID
	private String userId;			//交钱的用户的ID
	private String paymentDate;		//交钱的日期
	private Float paymentAmount;	//交钱的数量
	
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workId) {
		this.workerId = workId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Float getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
}
