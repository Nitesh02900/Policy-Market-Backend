package com.techlabs.insurance.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.techlabs.insurance.entity.Agent;
import com.techlabs.insurance.entity.Employee;
import com.techlabs.insurance.exception.CustomerNotFoundException;
import com.techlabs.insurance.payload.AgentDto;
import com.techlabs.insurance.payload.GetAgentDto;
import com.techlabs.insurance.payload.GetEmployeeDto;
import com.techlabs.insurance.payload.UpdateCustomer;
import com.techlabs.insurance.service.AgentService;

@RestController
@RequestMapping("/insuranceapp")
public class AgentController {

	
	@Autowired  
	private AgentService agentService;
	
//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/agent")
	public  ResponseEntity<String> AddAgent(@RequestBody AgentDto agentDto)
	{
		String response= agentService.addAgent(agentDto);
		 return new ResponseEntity<>(response,HttpStatus.CREATED); 
	}
	
	@GetMapping("/agents")
	public ResponseEntity<Page<GetAgentDto>> getAllAgent(@RequestParam Map<String,String>params)
	{
		Page<GetAgentDto> agentPage = null;
		int pagenumber =0;
		int pagesize = 30;
	
		if(params.isEmpty())
		{
			agentService.findAllAgent();
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
	
		agentPage = agentService.getAllAgentPageWise(pagenumber, pagesize);
		
		if(agentPage.getTotalElements()==0)
		{
			 throw new CustomerNotFoundException();
		}
		HttpHeaders header  = new HttpHeaders();
		header.set("X-Total-Count", String.valueOf(agentPage.getTotalElements()));
		return ResponseEntity.ok().headers(header).body(agentPage);
		
	}
	
	@GetMapping("/agent")
	 public ResponseEntity<Agent> getAdminByUserName(@RequestParam String username)
	 {
		 Agent agent = agentService.findAgentByUsername(username);
		 if(agent==null)
		 {
			 return ResponseEntity.notFound().build();
		 }
		 else
		 {
			 return ResponseEntity.ok(agent);
		 }
	 }
	
	@PutMapping("/updateagent/{username}")
	public ResponseEntity<String> updateCustomerDetails( @PathVariable(name = "username") String username,@RequestBody UpdateCustomer updateAgentData) {
		 System.out.println("Received UpdateAgent payload: " + updateAgentData.toString());
	    String response = agentService.updateAgentDetailsByUsername(username, updateAgentData);
	    return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
