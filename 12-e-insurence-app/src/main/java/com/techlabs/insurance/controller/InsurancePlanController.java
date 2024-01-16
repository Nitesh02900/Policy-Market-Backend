package com.techlabs.insurance.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entity.InsurancePlan;
import com.techlabs.insurance.entity.InsuranceScheme;
import com.techlabs.insurance.exception.PlanNotFoundException;
import com.techlabs.insurance.exception.SchemeNotFoundException;
import com.techlabs.insurance.service.InsurancePlanService;


@RestController
@RequestMapping("/insuranceapp")
public class InsurancePlanController {

	@Autowired  
	private InsurancePlanService insurancePlanService;
	
//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/insuranceplan")
	public  ResponseEntity<String> AddInsurancePlan(@RequestBody InsurancePlan insurancePlan)
	{
		
		String response= insurancePlanService.addInsurancePlan(insurancePlan);
		 return new ResponseEntity<>(response,HttpStatus.CREATED); 
	}
	
	@GetMapping("/plans")
	public ResponseEntity<Page<InsurancePlan>> getAllPlans(@RequestParam Map<String,String>params)
	{
		Page<InsurancePlan> planPage = null;
		int pagenumber =0;
		int pagesize = 30;
	
		if(params.isEmpty())
		{
			insurancePlanService.findAllPlan();
		}
		else
		{
			
			if(params.containsKey("pagenumber"))
			{
				pagenumber = Integer.parseInt(params.get("pagenumber"));
			}
			 if (params.containsKey("pagesize")) {
			       
				 pagesize = Integer.parseInt(params.get("pagesize"));
			 } 
		}
		planPage =insurancePlanService.getAllPlanPageWise(pagenumber, pagesize);
			
		if(planPage.getTotalElements()==0)
		{
			 throw new PlanNotFoundException();
		}
		HttpHeaders header  = new HttpHeaders();
		header.set("X-Total-Count", String.valueOf(planPage.getTotalElements()));
		return ResponseEntity.ok().headers(header).body(planPage);
	}
	
	@GetMapping("/planschemes")
	public ResponseEntity<Page<InsuranceScheme>> getAllScheme(@RequestParam Map<String,String>params)
	{
		Page<InsuranceScheme> schemePage = null;
		int pagenumber =0;
		int pagesize = 30;
		int planId = 0;
			
			if(params.containsKey("pagenumber"))
			{
				pagenumber = Integer.parseInt(params.get("pagenumber"));
			}
			 if (params.containsKey("pagesize")) {
			       
				 pagesize = Integer.parseInt(params.get("pagesize"));
			 } 
			 if (params.containsKey("planId")) {
			       
				 planId = Integer.parseInt(params.get("planId"));
			 } 
		
		schemePage =insurancePlanService.getAllSchemePageWise(pagenumber, pagesize, planId);
			
		if(schemePage.getTotalElements()==0)
		{
			 throw new SchemeNotFoundException();
		}
		HttpHeaders header  = new HttpHeaders();
		header.set("X-Total-Count", String.valueOf(schemePage.getTotalElements()));
		return ResponseEntity.ok().headers(header).body(schemePage);
	}
	
	
	@PutMapping("/plan/{planId}/{status}")
	public  ResponseEntity<String> UpdateCustomer( @PathVariable(name = "planId") int planId,@PathVariable(name = "status") String status)
	{
		String response = insurancePlanService.UpadatePlanStatus(planId, status);
				
		return new ResponseEntity<>(response,HttpStatus.CREATED); 
	}
}
