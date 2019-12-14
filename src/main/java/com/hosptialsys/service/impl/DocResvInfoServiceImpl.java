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
	public List<String> findDate(String userName, String drDepartment) {
		return docResInfoMapper.findDate(userName, drDepartment);
	}
	
	@Override
	public List<String> findTimeSlot(String userName, String drDepartment, String drDate) {
		return docResInfoMapper.findTimeSlot(userName, drDepartment, drDate);
	}

	@Override
	public List<String> findDate1(String drDepartment) {
		return docResInfoMapper.findDate1(drDepartment);
	}

	@Override
	public List<String> findTimeSlot1(String drDepartment, String drDate) {
		return docResInfoMapper.findTimeSlot1(drDepartment, drDate);
	}

	@Override
	public DocResInfo findDocInfo1(String drDepartment, String drTimeSlot, String drDate) {
		
		return docResInfoMapper.findDocInfo1(drDepartment, drTimeSlot, drDate);
	}
}
