package com.techlabs.insurance.payload;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techlabs.insurance.entity.Agent;
import com.techlabs.insurance.entity.Customer;
import com.techlabs.insurance.entity.FileDB;
import com.techlabs.insurance.entity.InsuranceScheme;
import com.techlabs.insurance.entity.Nominee;
import com.techlabs.insurance.entity.Payment;
import com.techlabs.insurance.entity.Policy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class PolicyDataDto {

	 
	private int policyNumber;
	private String customerName;
	private String planName;
	private String schemeName;
	private int noOfYear;
	private Date date;
	
	private double totalPremiumAmount; 
	private double installmentAmount;
	
	private int premiumType;
	
	private double commission;

	private boolean status;
	private boolean active;
	
//	public static PolicyDataDto fromPolicy(Policy policy) {
//        return new PolicyDataDto(
//                policy.getPolicyNumber(),
//                policy.getNumberOfYear(),
//                policy.getTotalPremiumAmount(),
//                policy.getInstallmentAmount(),
//
//                policy.getPremiumType(),
//                policy.getScheme().getSchemeName(),
//                policy.getScheme().getPlan().getPlanName()
//        );
//    }
}
