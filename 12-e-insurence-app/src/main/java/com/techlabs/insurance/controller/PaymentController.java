package com.techlabs.insurance.controller;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entity.Payment;
import com.techlabs.insurance.payload.GetCustomerDto;
import com.techlabs.insurance.payload.PaymentDto;
import com.techlabs.insurance.service.PaymentService;


@RestController
@RequestMapping("/insuranceapp")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/payment/{policyNumber}")
	public  ResponseEntity<String> AddPayment(@RequestBody Payment payment, @PathVariable(name = "policyNumber") int policyNumber)
	{
		
		String response =  paymentService.addPayment(payment,policyNumber);
				
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/payments")
	public ResponseEntity<List<PaymentDto>> getAllPayment()
	{
		List<PaymentDto> payments = paymentService.getAllPayment();
		  return new ResponseEntity<>(payments,HttpStatus.CREATED); 
	}
	
}
