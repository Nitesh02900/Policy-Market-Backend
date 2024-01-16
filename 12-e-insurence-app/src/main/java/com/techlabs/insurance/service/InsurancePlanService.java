package com.techlabs.insurance.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entity.InsurancePlan;
import com.techlabs.insurance.entity.InsuranceScheme;

public interface InsurancePlanService {
	String addInsurancePlan(InsurancePlan insurnacePlan);
	Page<InsurancePlan> getAllPlanPageWise(int pageNumber, int pagesize);
	List<InsurancePlan> findAllPlan();
	Page<InsuranceScheme> getAllSchemePageWise(int pageNumber, int pageSize, int planId);
	List<InsuranceScheme> findAllSchemeByPlanId(int planId);
	String UpadatePlanStatus(int planId,String status );
	
}
