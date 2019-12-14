package com.hosptialsys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosptialsys.domain.Worker;
import com.hosptialsys.mapper.WorkerMapper;
import com.hosptialsys.service.WorkerService;

@Service
public class WorkerServiceImpl implements WorkerService{

	@Autowired
	private WorkerMapper workerMapper;

	public int saveWorker(Worker worker) {
		return workerMapper.save(worker);
	}

	public Worker findById(String userId) {
		return workerMapper.findById(userId);
	}

	public Worker findByName(String userName) {
		return workerMapper.findByName(userName);
	}

	public Worker findUser(String userId, String userPassword) {
		return workerMapper.findUser(userId, userPassword);	
	}

	@Override
	public List<String> findByDep(String workDepartment) {
		return workerMapper.findByDep(workDepartment);
	}

	@Override
	public String findIdByName(String userName) {
		return workerMapper.findIdByName(userName);
	}
	
}
