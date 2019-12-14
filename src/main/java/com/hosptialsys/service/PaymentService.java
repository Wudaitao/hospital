package com.hosptialsys.service;

import com.hosptialsys.domain.Payment;

public interface PaymentService {
	Payment findById(Integer paymentId);
	Payment findByJointKey(String userId, String workerId, String paymentDate);
	
	int save(Payment payment);
	int updatePayment(String userId, String paymentDate, Float addAmount);
}
