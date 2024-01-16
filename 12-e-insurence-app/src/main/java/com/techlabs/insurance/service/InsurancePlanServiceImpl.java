package com.techlabs.insurance.service;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entity.InsurancePlan;
import com.techlabs.insurance.entity.InsuranceScheme;
import com.techlabs.insurance.payload.GetPlanDto;
import com.techlabs.insurance.repository.InsurancePlanRepository;


@Service
public class InsurancePlanServiceImpl implements InsurancePlanService{

	@Autowired
	private InsurancePlanRepository insurancePlanRepo;
	
	
	@Override
	public String addInsurancePlan(InsurancePlan insuranacePlan) {
		insurancePlanRepo.save(insuranacePlan);
		return "Insurance Plan added Successfully"; 
	}

	public Page<InsurancePlan> getAllPlanPageWise(int pageNumber, int pageSize)
	{
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<InsurancePlan> planPage = insurancePlanRepo.findAll(pageable);
		return planPage;
		
		
	
	}
	public GetPlanDto mapToDto(InsurancePlan plan) {
	    return new GetPlanDto(plan.getPlanName(), plan.isActive());
	}
	
	public List<InsurancePlan> findAllPlan()
	{
		return insurancePlanRepo.findAll();
	}
	public Page<InsuranceScheme> getAllSchemePageWise(int pageNumber, int pageSize, int planId)
	{
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Optional<InsurancePlan> plans =insurancePlanRepo.findById(planId);
		InsurancePlan plan = null;
		if(plans.isPresent())
		{
			plan = plans.get();
			
			 List<InsuranceScheme> schemeList = plan.getSchemes();
		     int start = (int) pageable.getOffset();
		     int end = Math.min((start + pageable.getPageSize()), schemeList.size());

		     List<InsuranceScheme> subList = schemeList.subList(start, end);
		     Page<InsuranceScheme> schemePage = new PageImpl<>(subList, pageable, schemeList.size());

		        return schemePage;
			
		}
		else {
		return null;
		}
    
		
	}
	
	public List<InsuranceScheme> findAllSchemeByPlanId(int planId)
	{

		Optional<InsurancePlan> plans =insurancePlanRepo.findById(planId);
		InsurancePlan plan = null;
		if(plans.isPresent())
		{
			plan = plans.get();
			return plan.getSchemes();
		}
		return null;
		
	}

	@Override
	public String UpadatePlanStatus(int planId, String status) {
		Optional<InsurancePlan> plans =  insurancePlanRepo.findById(planId);
		InsurancePlan plan =null;
		if(plans.isPresent())
		{
			plan = plans.get();
			
		
			if("True".equals(status))
			{
				plan.setActive(true);
				insurancePlanRepo.save(plan);
	
				return "Status updated true";
			}
			else
			{
				plan.setActive(false);
				insurancePlanRepo.save(plan);
				return "Status updated false";
			}
		}
		
		return "Scheme Doesn't exist";
		
	}

}
