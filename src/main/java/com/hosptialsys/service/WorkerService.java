package com.hosptialsys.service;

import java.util.List;

import com.hosptialsys.domain.Worker;

public interface WorkerService {
	
	int saveWorker(Worker worker);
	
	Worker findById(String userId);
	
	String findIdByName(String userName);
	
	Worker findByName(String userName);
	
	Worker findUser(String userId, String userPassword);
	
	List<String> findByDep(String workDepartment);
}
