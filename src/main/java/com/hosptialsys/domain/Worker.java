package com.hosptialsys.domain;

/**
 * @fuction ：医院工作人员类
 * @author  ：NONWORD
 * @version ：2019年12月6日 上午11:24:59
 */
public class Worker extends User{

	private String workerType;       //工作岗位：专家医生，医生，挂号人员，财务人员，药剂师
	private String workerDepartment; //所属部门：儿科，外科
	
	public Worker(String userId, String userName, String userPassword, 
			String userGender, String userAge, String workerType, String workerDepartment) {
		super(userId, userName, userPassword, userGender, userAge);
		this.workerType = workerType;
		this.workerDepartment = workerDepartment;
	}
	
	public String getWorkerType() {
		return workerType;
	}
	
	public void setWorkerType(String workerType) {
		this.workerType = workerType;
	}
	
	public String getWorkerDepartment() {
		return workerDepartment;
	}
	
	public void setWorkerDepartment(String workerDepartment) {
		this.workerDepartment = workerDepartment;
	}
}
