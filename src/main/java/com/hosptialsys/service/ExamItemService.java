package com.hosptialsys.service;

import java.util.List;

import com.hosptialsys.domain.ExamItem;

public interface ExamItemService {

	ExamItem findById(Integer checkItemId);
	
	List<ExamItem> findByUserDate(String userId,String checkDate);
	
	Float checkSum(String userId,String checkDate);
	
	Float getTotal(String userId,String checkDate);
	
	int save(ExamItem examItem);
	
	int updateCheckIsPaid(String userId, String checkDate, String checkIsPaid);
	
	int updateCheckResult(Integer checkItemId, String checkResult);
}
