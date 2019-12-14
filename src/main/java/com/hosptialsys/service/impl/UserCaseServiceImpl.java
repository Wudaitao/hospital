package com.hosptialsys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosptialsys.domain.UserCase;
import com.hosptialsys.mapper.UserCaseMapper;
import com.hosptialsys.service.UserCaseService;

@Service
public class UserCaseServiceImpl implements UserCaseService {

	@Autowired
	private UserCaseMapper userCaseMapper;

	@Override
	public UserCase findById(Integer caseId) {
		return userCaseMapper.findById(caseId);
	}

	@Override
	public List<UserCase> findByUserId(String userId) {
		return userCaseMapper.findByUserId(userId);
	}

	@Override
	public int save(UserCase userCase) {
		userCaseMapper.save(userCase);
		int caseId = userCase.getCaseId();
		return caseId;
	}

	@Override
	public int updateCaseIsFinish(UserCase userCase) {
		return userCaseMapper.updateCaseIsFinish(userCase);
	}

	@Override
	public UserCase findByUserDate(String userId, String caseDate) {
		return userCaseMapper.findByUserDate(userId, caseDate);
	}
	
	
}
