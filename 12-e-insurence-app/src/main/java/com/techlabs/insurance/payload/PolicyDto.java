package com.techlabs.insurance.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class PolicyDto {

//	private int numberOfYear;
	private double totalPremiumAmount; 
	private double installmentAmount;
	private double intrestAmount;
	
	private int premiumType;
	private double totalAmount;
}
