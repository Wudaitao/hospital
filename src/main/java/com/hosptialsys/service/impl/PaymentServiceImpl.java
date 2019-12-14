package com.hosptialsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosptialsys.domain.Payment;
import com.hosptialsys.mapper.PaymentMapper;
import com.hosptialsys.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private PaymentMapper paymentMapper;
	
	@Override
	public Payment findById(Integer paymentId) {
		return paymentMapper.findById(paymentId);
	}

	@Override
	public Payment findByJointKey(String userId, String workerId, String paymentDate) {
		return  paymentMapper.findByJointKey(userId, workerId, paymentDate);
	}

	@Override
	public int save(Payment payment) {
		return paymentMapper.save(payment);
	}

	@Override
	public int updatePayment(String userId, String paymentDate, Float addAmount) {
		return paymentMapper.updatePayment(userId, paymentDate, addAmount);
	}
	
}
