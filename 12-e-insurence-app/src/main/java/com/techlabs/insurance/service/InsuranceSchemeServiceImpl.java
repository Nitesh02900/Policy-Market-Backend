package com.techlabs.insurance.service;


import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entity.InsurancePlan;
import com.techlabs.insurance.entity.InsuranceScheme;
import com.techlabs.insurance.entity.Policy;
import com.techlabs.insurance.payload.PolicyDto;
import com.techlabs.insurance.repository.InsurancePlanRepository;
import com.techlabs.insurance.repository.InsuranceSchemeRepository;

@Service
public class InsuranceSchemeServiceImpl implements InsuranceSchemeService{

	@Autowired
	private InsuranceSchemeRepository insuranceSchemeRepo;
	@Autowired
	private InsurancePlanRepository insurancePlanRepo;
	
	
	@Override
	public InsuranceScheme findSchemeBySchemeId(int schemeId) {
		Optional<InsuranceScheme> schemes = insuranceSchemeRepo.findById(schemeId);
		if(schemes.isPresent())
		{
			return schemes.get();
		}
		return null;
	}
	
	@Override
	public Page<InsuranceScheme> getAllSchemePageWise(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<InsuranceScheme> schemePage = insuranceSchemeRepo.findSchemeByActive(true, pageable);
		return schemePage;
		
		
	}
	@Override
	public List<InsuranceScheme> findAllScheme() {
		
		return insuranceSchemeRepo.findAll();
	}
	@Override
	public String addSchemeByPlanId(int planId, InsuranceScheme insuranceScheme) {
		Optional<InsurancePlan> plans = insurancePlanRepo.findById(planId);
		InsurancePlan plan=null;
		 if (!plans.isEmpty()) {
		        plan = plans.get();
		        insuranceScheme.setPlan(plan); // Set the plan for the insuranceScheme
		        insuranceScheme.setActive(true);
		        plan.getSchemes().add(insuranceScheme); // Add the insuranceScheme to the plan's schemes
		        insurancePlanRepo.save(plan); // Save the updated plan
		        return "Scheme is registered";
		  } 
		 else {
		        return "Invalid planId"; // Handle the case where the planId is not found
		    }
	}

	@Override
	public String UpadateSchemeStatus(int schemeId, String status) {
		Optional<InsuranceScheme> schemes =  insuranceSchemeRepo.findById(schemeId);
		InsuranceScheme scheme =null;
		if(schemes.isPresent())
		{
			scheme = schemes.get();
			
		
			if("True".equals(status))
			{
				scheme.setActive(true);
				insuranceSchemeRepo.save(scheme);
	
				return "Status updated true";
			}
			else
			{
				scheme.setActive(false);
				insuranceSchemeRepo.save(scheme);
				return "Status updated false";
			}
		}
		
		return "Scheme Doesn't exist";
	}
	
	
	

}
