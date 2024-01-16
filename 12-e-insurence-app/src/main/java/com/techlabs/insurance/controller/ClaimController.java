package com.techlabs.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entity.Claim;
import com.techlabs.insurance.payload.ClaimDto;
import com.techlabs.insurance.payload.PaymentDto;
import com.techlabs.insurance.service.ClaimService;

@RestController
@RequestMapping("/insuranceapp")
public class ClaimController {

	
	@Autowired
	private ClaimService claimService;
	
	@PostMapping("/claim/{policyNumber}")
	public  ResponseEntity<String> AddClaim(@RequestBody Claim claim, @PathVariable(name = "policyNumber") int policyNumber)
	{
		String response = claimService.addClaim(claim,policyNumber);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		
	}
	@GetMapping("/claims")
	public ResponseEntity<List<ClaimDto>> getAllPayment()
	{
		List<ClaimDto> claims = claimService.getAllClaim();
		  return new ResponseEntity<>(claims,HttpStatus.CREATED); 
	}
	
	
	
}
