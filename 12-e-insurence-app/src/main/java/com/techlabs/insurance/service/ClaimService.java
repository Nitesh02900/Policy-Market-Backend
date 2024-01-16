package com.techlabs.insurance.service;

import java.util.List;

import com.techlabs.insurance.entity.Claim;
import com.techlabs.insurance.payload.ClaimDto;

public interface ClaimService {

	String addClaim(Claim claim, int policyNumber);
	List<ClaimDto> getAllClaim();
}
