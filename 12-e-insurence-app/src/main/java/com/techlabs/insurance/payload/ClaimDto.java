package com.techlabs.insurance.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ClaimDto {

	private int claimId;
	private int installmentNo ;
	private long CardNumber ;
	private double amount;
	private String nameOnCard;
	private Date date;
	private String status;
	private int PolicyNo;
}
