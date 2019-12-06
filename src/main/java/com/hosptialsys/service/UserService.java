package com.hosptialsys.service;

import com.hosptialsys.domain.User;

public interface UserService {

	int saveUser(User user);
	
	User findById(String userId);
	
	User findUser(String userId, String userPassword);
}
