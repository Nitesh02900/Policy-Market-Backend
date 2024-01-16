package com.techlabs.insurance.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entity.Policy;
import com.techlabs.insurance.payload.DocumentDto;
import com.techlabs.insurance.payload.PolicyDataDto;

public interface PolicyService {

	List<Policy> getAllPolicy();
	Page<PolicyDataDto> getAllPolicyPageWise(int pageNumber, int pageSize);
	Page<PolicyDataDto> getAllPolicyPageWiseAndUsername(int pageNumber, int pageSize, String username);
	 List<DocumentDto> getalldocument(int policynumber);
	 String updatePolicyStatus( int policyNumber, String status);
	 Page<PolicyDataDto> getAllPolicyByPageWiseAndAgent(int pageNumber, int pageSize, String username);
}
