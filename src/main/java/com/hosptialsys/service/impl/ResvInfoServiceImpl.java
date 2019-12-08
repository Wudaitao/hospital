package com.hosptialsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosptialsys.domain.ResInfo;
import com.hosptialsys.mapper.ResInfoMapper;
import com.hosptialsys.service.ResInfoService;

@Service
public class ResvInfoServiceImpl implements ResInfoService{

	@Autowired
	private ResInfoMapper resInfoMapper;
	
	@Override
	public ResInfo findById(Integer resvId) {
		return resInfoMapper.findById(resvId);
	}

	@Override
	public int save(ResInfo resInfo) {
		resInfoMapper.save(resInfo);
		Integer resvId = resInfo.getResvId();
		return resvId;
	}

	@Override
	public int updateState(ResInfo resInfo) {
		return resInfoMapper.updateState(resInfo);
	}

}
