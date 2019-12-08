package com.hosptialsys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosptialsys.domain.DocResInfo;
import com.hosptialsys.mapper.DocResInfoMapper;
import com.hosptialsys.service.DocResvInfoService;

@Service
public class DocResvInfoServiceImpl implements DocResvInfoService{

	@Autowired 
	private DocResInfoMapper docResInfoMapper;
	@Override
	public DocResInfo findById(Integer drId) {
		return docResInfoMapper.findById(drId);
	}


	@Override
	public int save(DocResInfo docResInfo) {
		return docResInfoMapper.save(docResInfo);
	}

	@Override
	public int update(DocResInfo docResInfo) {
		return docResInfoMapper.updateDocResInfo(docResInfo);
	}


	@Override
	public DocResInfo findDocInfo(String doctorId, String drDepartment, String drTimeSlot, String drDate) {
		return docResInfoMapper.findDocInfo(doctorId, drDepartment, drTimeSlot, drDate);
	}



	@Override
	public List<DocResInfo> findAll(String userId, String drDepartment) {
		return docResInfoMapper.findAll(userId, drDepartment);
	}
	
}
