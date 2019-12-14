package com.hosptialsys.domain;

public class MedicineList {
	private Integer mlId;  		    //药品单的ID，主键 
	private String  userId;		    //药品单所属的用户ID
	private String  mlDoctorId; 	//开药的医生的ID
	private String  medName;		//药品的名字
	private Integer medNum;		    //药品的数量
	private String  mlDosage;       //药的用量
	private String  mlDate;		    //药品单生成的日期
	private Float   mlTotalPrice;	//药品单的总价（这里就是前面的药价x药的数量）
	private String  mlIsPaid;	    //药品单是否已付款
	private String  mlState;        //药单状态
	
	public Integer getMlId() {
		return mlId;
	}
	public void setMlId(Integer mlId) {
		this.mlId = mlId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMlDate() {
		return mlDate;
	}
	public void setMlDate(String mlDate) {
		this.mlDate = mlDate;
	}

	public Integer getMedNum() {
		return medNum;
	}
	public void setMedNum(Integer medNum) {
		this.medNum = medNum;
	}
	public Float getMlTotalPrice() {
		return mlTotalPrice;
	}
	public void setMlTotalPrice(Float mlTotalPrice) {
		this.mlTotalPrice = mlTotalPrice;
	}
	public String getMlIsPaid() {
		return mlIsPaid;
	}
	public void setMlIsPaid(String mlIsPaid) {
		this.mlIsPaid = mlIsPaid;
	}
	public String getMlDoctorId() {
		return mlDoctorId;
	}
	public void setMlDoctorId(String mlDoctorId) {
		this.mlDoctorId = mlDoctorId;
	}
	public String getMedName() {
		return medName;
	}
	public void setMedName(String medName) {
		this.medName = medName;
	}
	public String getMlDosage() {
		return mlDosage;
	}
	public void setMlDosage(String mlDosage) {
		this.mlDosage = mlDosage;
	}
	public String getMlState() {
		return mlState;
	}
	public void setMlState(String mlState) {
		this.mlState = mlState;
	}
	
}
