package com.hosptialsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosptialsys.domain.User;
import com.hosptialsys.mapper.UserMapper;
import com.hosptialsys.service.UserService;

@Service
public class UserSerciveImpl implements UserService{

	@Autowired 
	private UserMapper userMapper;
	public int saveUser(User user) {
		if (userMapper.findById(user.getUserId()) == null) {
			return userMapper.save(user);
		}
		else {
			return -1;
		}
	}

	public User findById(String userId) {
		return userMapper.findById(userId);
	}

	public User findUser(String userId, String userPassword) {
		return userMapper.findUser(userId, userPassword);
	}

}
