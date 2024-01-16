package com.techlabs.insurance.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entity.InsuranceScheme;
import com.techlabs.insurance.payload.PolicyDto;

public interface InsuranceSchemeService {

	InsuranceScheme findSchemeBySchemeId(int schemeId);
	
	Page<InsuranceScheme> getAllSchemePageWise(int pageNumber, int pageSize);
	List<InsuranceScheme> findAllScheme();
	String addSchemeByPlanId(int planId, InsuranceScheme insuranceScheme);

	String UpadateSchemeStatus(int schemeId,String status );
	
}
