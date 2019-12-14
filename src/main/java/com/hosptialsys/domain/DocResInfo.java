package com.hosptialsys.domain;

/**
 * @fuction ：医生或科室的预约信息类
 * @author  ：NONWORD
 * @version ：2019年12月6日 上午11:32:29
 */
public class DocResInfo {
	
	private Integer drId;         //预约信息ID
	private String  userId;       //医生ID
	private String  userName;     //医生姓名
	private Integer drResvNum;    //该医生或科室已预约人数
	private Integer drMaxResvNum; //该医生或科室最大预约人数
	private String  drDepartment; //所属科室
	private String  drTimeSlot;   //时间段
	private String  drDate;       //日期
	
	public Integer getDrId() {
		return drId;
	}
	
	public void setDrId(Integer drId) {
		this.drId = drId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Integer getDrResvNum() {
		return drResvNum;
	}
	
	public void setDrResvNum(Integer drResvNum) {
		this.drResvNum = drResvNum;
	}
	
	public Integer getDrMaxResvNum() {
		return drMaxResvNum;
	}
	
	public void setDrMaxResvNum(Integer drMaxResvNum) {
		this.drMaxResvNum = drMaxResvNum;
	}
	
	public String getDrDepartment() {
		return drDepartment;
	}
	
	public void setDrDepartment(String drDepartment) {
		this.drDepartment = drDepartment;
	}
	
	public String getDrTimeSlot() {
		return drTimeSlot;
	}
	
	public void setDrTimeSlot(String drTimeSlot) {
		this.drTimeSlot = drTimeSlot;
	}
	
	public String getDrDate() {
		return drDate;
	}
	
	public void setDrDate(String drDate) {
		this.drDate = drDate;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
