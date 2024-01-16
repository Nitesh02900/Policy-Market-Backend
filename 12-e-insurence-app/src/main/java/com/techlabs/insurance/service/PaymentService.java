package com.techlabs.insurance.service;

import java.util.List;

import com.techlabs.insurance.entity.Payment;
import com.techlabs.insurance.payload.PaymentDto;

public interface PaymentService {

	

	String addPayment(Payment payment, int policyNumber);
	List<PaymentDto> getAllPayment();
	
}
