package com.hosptialsys.service;


import java.util.List;

import com.hosptialsys.domain.DocResInfo;

public interface DocResvInfoService {
	DocResInfo findById(Integer drId);
	
	DocResInfo findDocInfo(String doctorId, String drDepartment, String drTimeSlot, String drDate);

	List<DocResInfo> findAll(String userId, String drDepartment);
	
	int save(DocResInfo docResInfo);
	
	int update(DocResInfo docResInfo);

}
