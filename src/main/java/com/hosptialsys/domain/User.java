package com.hosptialsys.domain;

public class User {
	
	private String  userId;

	private String  userName;
	private String  userGender;
	private String  userAge;
	
	public User(String userId, String userName, String userGender, String userAge) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userGender = userGender;
		this.userAge = userAge;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserGender() {
		return userGender;
	}
	
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	
	public String getUserAge() {
		return userAge;
	}
	
	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}
	
}
