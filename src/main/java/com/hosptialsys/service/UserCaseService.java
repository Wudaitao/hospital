package com.hosptialsys.service;

import java.util.List;

import com.hosptialsys.domain.UserCase;

public interface UserCaseService {

	UserCase findById(Integer caseId);
	
	List<UserCase> findByUserId(String userId);
	
	UserCase findByUserDate(String userId, String caseDate);
	
	int save(UserCase userCase);
	
	int updateCaseIsFinish(UserCase userCase);
}
