package com.hosptialsys.service;

import com.hosptialsys.domain.ResInfo;

public interface ResInfoService {
	
	ResInfo findById(Integer resvId);
	
	int save(ResInfo resInfo);
	
	int updateState(ResInfo resInfo);
}
