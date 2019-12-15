package com.hosptialsys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosptialsys.domain.MedicineList;
import com.hosptialsys.mapper.MedicineListMapper;
import com.hosptialsys.service.MedicineListService;
import com.hosptialsys.utils.CommonUtil;

@Service
public class MedicineListServiceImpl implements MedicineListService{
	
	@Autowired 
	private MedicineListMapper medicineListMapper;
	
	@Override
	public MedicineList findById(Integer medicineListId) {
		return medicineListMapper.findById(medicineListId);
	}
	
	@Override
	public List<MedicineList> findByUserIdAndDate(String userId, String mlDate) {
		return medicineListMapper.findByUserIdAndDate(userId, CommonUtil.getFormatedSystemTime());
	}
	
	@Override
	public int save(MedicineList ml) {
		return medicineListMapper.save(ml);
	}

	@Override
	public int updateIsPaid(String userId, String mlDate, String mlIsPaid) {
		return medicineListMapper.updateIsPaid(userId, mlDate, mlIsPaid);
	}

	@Override
	public Float getTatal(String userId, String mlDate) {
		return medicineListMapper.getTotal(userId, mlDate);
	}

	@Override
	public Float returnTatal(String userId, String mlDate) {
		return medicineListMapper.returnTotal(userId, mlDate);
	}

	@Override
	public int updateMlState(Integer medicineListId, String newState) {
		return medicineListMapper.updateMlState(medicineListId, newState);
	}
	
}
