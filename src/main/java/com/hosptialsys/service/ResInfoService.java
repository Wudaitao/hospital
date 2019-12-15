package com.hosptialsys.service;


import com.hosptialsys.domain.ResInfo;

public interface ResInfoService {
	
	ResInfo findById(Integer resvId);
	
	int save(ResInfo resInfo);
	
	int updateState(String resvIsValid,Integer resvId);
	
	ResInfo findByUserIdAndDate(String userId,String resvDoctorId,String resvDepartment,String resvTimeSlot,String resvDate);

	ResInfo findByUserIdAndDate1(String userId,String resvDepartment,String resvTimeSlot,String resvDate);
}
