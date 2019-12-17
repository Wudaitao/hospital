package com.hosptialsys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosptialsys.domain.ExamItem;
import com.hosptialsys.mapper.ExamItemMapper;
import com.hosptialsys.service.ExamItemService;

@Service
public class ExamItemServiceImpl implements ExamItemService {

	@Autowired 
	private ExamItemMapper examItemMapper;

	@Override
	public ExamItem findById(Integer checkItemId) {
		return examItemMapper.findById(checkItemId);
	}

	@Override
	public List<ExamItem> findByUserDate(String userId, String checkDate) {
		return examItemMapper.findByUserDate(userId, checkDate);
	}

	@Override
	public Float checkSum(String userId, String checkDate) {
		return examItemMapper.checkSum(userId, checkDate);
	}

	@Override
	public int save(ExamItem examItem) {
		examItemMapper.save(examItem);
		int checkItemId = examItem.getCheckItemId();
		return checkItemId;
	}

	@Override
	public int updateCheckIsPaid(String userId, String checkDate, String checkIsPaid) {
		return examItemMapper.updateCheckIsPaid(userId, checkDate, checkIsPaid);
	}

	@Override
	public int updateCheckResult(Integer checkItemId, String checkResult, String checkDoctorId) {
		return examItemMapper.updateCheckResult(checkItemId, checkResult, checkDoctorId);
	}

	@Override
	public Float getTotal(String userId, String checkDate) {
		return examItemMapper.getTotal(userId, checkDate);
	}
	
	
}
