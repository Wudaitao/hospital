package com.hosptialsys.service;


import java.util.List;

import com.hosptialsys.domain.DocResInfo;

public interface DocResvInfoService {
	DocResInfo findById(Integer drId);
	
	DocResInfo findDocInfo(String doctorId, String drDepartment, String drTimeSlot, String drDate);

	DocResInfo findDocInfo1(String drDepartment, String drTimeSlot, String drDate);

	List<String> findDate(String userName, String drDepartment);
	
	List<String> findDate1(String drDepartment);

	List<String> findTimeSlot(String userName, String drDepartment, String drDate);
	
	List<String> findTimeSlot1(String drDepartment, String drDate);

	int save(DocResInfo docResInfo);
	
	int update(DocResInfo docResInfo);

}
