package com.hosptialsys.service;

import com.hosptialsys.domain.Worker;

public interface WorkerService {
	
	int saveWorker(Worker worker);
	
	Worker findById(String userId);
	
	Worker findByName(String userName);
	
	Worker findUser(String userId, String userPassword);
}
