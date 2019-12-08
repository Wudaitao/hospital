package com.hosptialsys.domain;

/**
 * @fuction ：预约成功信息类
 * @author  ：NONWORD
 * @version ：2019年12月6日 下午9:10:54
 */
public class ResInfo {
	
	private Integer resvId;           //预约信息ID
	private String  userId;           //预约病人的ID
	private String  resvDoctorId;     //预约的医生ID
	private String  resvDepartment;   //预约医生所属科室
	private String  resvDate;         //预约的日期
	private String  resvTimeSlot;     //预约的时间段
	private String  resvType;         //预约类型，专家或者科室
	private String  resvIsValid;      //预约是否有效
	private Integer resvNum;          //预约号，区分先后顺序
	private String  resvOnline;       //线上或线下预约:0:线下，1：线上

	public String getResvOnline() {
		return resvOnline;
	}

	public void setResvOnline(String resvOnline) {
		this.resvOnline = resvOnline;
	}

	public Integer getResvId() {
		return resvId;
	}
	
	public void setResvId(Integer resvId) {
		this.resvId = resvId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getResvDoctorId() {
		return resvDoctorId;
	}
	
	public void setResvDoctorId(String resvDoctorId) {
		this.resvDoctorId = resvDoctorId;
	}
	
	public String getResvDepartment() {
		return resvDepartment;
	}
	
	public void setResvDepartment(String resvDepartment) {
		this.resvDepartment = resvDepartment;
	}
	
	public String getResvDate() {
		return resvDate;
	}
	
	public void setResvDate(String resvDate) {
		this.resvDate = resvDate;
	}
	
	public String getResvTimeSlot() {
		return resvTimeSlot;
	}
	
	public void setResvTimeSlot(String resvTimeSlot) {
		this.resvTimeSlot = resvTimeSlot;
	}
	
	public String getResvType() {
		return resvType;
	}
	
	public void setResvType(String resvType) {
		this.resvType = resvType;
	}
	
	public String getResvIsValid() {
		return resvIsValid;
	}
	
	public void setResvIsValid(String resvIsValid) {
		this.resvIsValid = resvIsValid;
	}
	
	public Integer getResvNum() {
		return resvNum;
	}
	
	public void setResvNum(Integer resvNum) {
		this.resvNum = resvNum;
	}
	
}
