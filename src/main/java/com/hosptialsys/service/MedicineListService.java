package com.hosptialsys.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hosptialsys.domain.MedicineList;

public interface MedicineListService {
	
	MedicineList findById(Integer medicineListId);
	
	List<MedicineList> findByUserIdAndDate(@Param("userId")String userId, @Param("mlDate")String mlDate);
	
	Float getTatal(String userId, String mlDate);
	
	Float returnTatal(String userId, String mlDate);
	
	int save(MedicineList ml);
	
	int updateIsPaid(String userId, String mlDate, String mlIsPaid);
	
	int updateMlState(Integer medicineListId, String newState);
}
