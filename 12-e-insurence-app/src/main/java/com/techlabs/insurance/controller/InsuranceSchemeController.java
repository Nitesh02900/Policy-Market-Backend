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
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entity.InsurancePlan;
import com.techlabs.insurance.entity.InsuranceScheme;
import com.techlabs.insurance.exception.PlanNotFoundException;
import com.techlabs.insurance.payload.PolicyDto;
import com.techlabs.insurance.payload.UpdateCustomer;
import com.techlabs.insurance.service.InsuranceSchemeService;


@RestController
@RequestMapping("/insuranceapp")
public class InsuranceSchemeController {

	@Autowired
	private InsuranceSchemeService insuranceSchemeService;
	
	@GetMapping("/insuranceScheme")
	public  ResponseEntity<InsuranceScheme> GetScheme(@RequestParam int schemeId)
	{
		 InsuranceScheme scheme=  insuranceSchemeService.findSchemeBySchemeId(schemeId);
		if(scheme==null)
		{
			 return ResponseEntity.notFound().build();
		 } 
		 else {
		     return ResponseEntity.ok(scheme);
		 }
	}
	
	
	
	@GetMapping("/schemes")
	public ResponseEntity<Page<InsuranceScheme>> getAllSchmes(@RequestParam Map<String,String>params)
	{
		Page<InsuranceScheme> schemePage = null;
		int pagenumber =0;
		int pagesize = 30;
	
		if(params.isEmpty())
		{
			insuranceSchemeService.findAllScheme();
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
		schemePage =insuranceSchemeService.getAllSchemePageWise(pagenumber, pagesize);
			
		if(schemePage.getTotalElements()==0)
		{
			 throw new PlanNotFoundException();
		}
		HttpHeaders header  = new HttpHeaders();
		header.set("X-Total-Count", String.valueOf(schemePage.getTotalElements()));
		return ResponseEntity.ok().headers(header).body(schemePage);
	}
	
	@PostMapping("/insurancescheme")
	public  ResponseEntity<String> AddInsuranceScheme(@RequestParam int planId, @RequestBody InsuranceScheme insuranceScheme)
	{
		
		String response= insuranceSchemeService.addSchemeByPlanId(planId, insuranceScheme);
		 return new ResponseEntity<>(response,HttpStatus.CREATED); 
	}
	
	@PutMapping("/scheme/{schemeId}/{status}")
	public  ResponseEntity<String> UpdateCustomer( @PathVariable(name = "schemeId") int schemeId,@PathVariable(name = "status") String status)
	{
		String response = insuranceSchemeService.UpadateSchemeStatus(schemeId, status);
				
		return new ResponseEntity<>(response,HttpStatus.CREATED); 
	}
	
	
	
	
}
