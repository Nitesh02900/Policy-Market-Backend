package com.techlabs.insurance.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entity.Customer;
import com.techlabs.insurance.exception.CustomerNotFoundException;
import com.techlabs.insurance.payload.CustomerDto;
import com.techlabs.insurance.payload.GetCustomerDto;
import com.techlabs.insurance.payload.UpdateCustomer;
import com.techlabs.insurance.payload.UpdatePassword;
import com.techlabs.insurance.service.CustomerService;


@RestController
@RequestMapping("/insuranceapp")
//@CrossOrigin
public class CustomerController {


	@Autowired  
	private CustomerService customerService;
//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/customer")
	public  ResponseEntity<String> AddCustomer(@RequestBody Customer customer)
	{
		String response= customerService.addCustomer(customer);
		 return new ResponseEntity<>(response,HttpStatus.CREATED); 
	}
	
	
	
	@GetMapping("/customer")
	public ResponseEntity<Customer> getCustomerByUsername (@RequestParam String username)
	{
		System.out.println(username);
		Customer customer = customerService.findCustomerByUsername(username);
		if(customer==null)
		{
			 return ResponseEntity.notFound().build();
		 } 
		 else {
		     return ResponseEntity.ok(customer);
		 }
	}
	
	@GetMapping("/customers")
	public ResponseEntity<Page<GetCustomerDto>> getAllCustomers(@RequestParam Map<String,String>params)
	{
		Page<GetCustomerDto> customerPage = null;
		int pagenumber =0;
		int pagesize = 30;
	
		if(params.isEmpty())
		{
			customerService.findAllCustomer();
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
		customerPage = customerService.getAllCustomerPageWise(pagenumber, pagesize);
		if(customerPage.getTotalElements()==0)
		{
			 throw new CustomerNotFoundException();
		}
		HttpHeaders header  = new HttpHeaders();
		header.set("X-Total-Count", String.valueOf(customerPage.getTotalElements()));
		return ResponseEntity.ok().headers(header).body(customerPage);
	}
	
	
	@GetMapping("/customersactive")
	public ResponseEntity<Page<GetCustomerDto>> getAllCustomersActive(@RequestParam Map<String,String>params)
	{
		Page<GetCustomerDto> customerPage = null;
		int pagenumber =0;
		int pagesize = 30;
	
		if(params.isEmpty()){
			customerService.findAllCustomer();
		}
		else{
			
			if(params.containsKey("pagenumber")){
				pagenumber = Integer.parseInt(params.get("pagenumber"));
			}
			 if (params.containsKey("pagesize")) {
			       
				 pagesize = Integer.parseInt(params.get("pagesize"));
			 } 
		}
		customerPage = customerService.getAllCustomerActivePageWise(pagenumber, pagesize);
		if(customerPage.getTotalElements()==0){
			 throw new CustomerNotFoundException();
		}
		HttpHeaders header  = new HttpHeaders();
		header.set("X-Total-Count", String.valueOf(customerPage.getTotalElements()));
		return ResponseEntity.ok().headers(header).body(customerPage);
	}
	
	
//	@PutMapping("/updatecustomerdetails")
//	public  ResponseEntity<String> UpdateCustomer( @RequestBody UpdateCustomer updateCustomerData)
//	{
//		String response = customerService.updateCustomerData( updateCustomerData);
//		return new ResponseEntity<>(response,HttpStatus.CREATED); 
//	}
	
	@PutMapping("/updatecustomer/{username}")
	public ResponseEntity<String> updateCustomerDetails(
	        @PathVariable(name = "username") String username,
	        @RequestBody UpdateCustomer updateCustomerData) {
	    System.out.println("Received UpdateCustomer payload: " + updateCustomerData.toString());
	    String response = customerService.updateCustomerDetailsByUsername(username, updateCustomerData);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@PutMapping("/registercustomer")
	public  ResponseEntity<String> UpdateCustomerdetails( @RequestBody CustomerDto customerDto )
	{
		String response = customerService.updateCustomerRegisterDetails( customerDto);
		return new ResponseEntity<>(response,HttpStatus.CREATED); 
	}
	
	@PutMapping("/customer/{username}/{status}")
	public  ResponseEntity<String> UpdateCustomer( @PathVariable(name = "username") String username,@PathVariable(name = "status") String status)
	{
		String response = customerService.UpadateCustomerStatus(username, status);
		return new ResponseEntity<>(response,HttpStatus.CREATED); 
	}
	
	@GetMapping("/customersagent")
	public ResponseEntity<Page<GetCustomerDto>> getAllCustomersByActiveAgent(@RequestParam Map<String,String>params)
	{
		Page<GetCustomerDto> customerPage = null;
		int pagenumber =0;
		int pagesize = 30;
		String username =null;
//		System.out.println("username-5555------>"+username);
		
	
		if(params.isEmpty()){
			customerService.findAllCustomer();
		}
		else{
			
			if(params.containsKey("pagenumber")){
				pagenumber = Integer.parseInt(params.get("pagenumber"));
			}
			 if (params.containsKey("pagesize")) {
			       
				 pagesize = Integer.parseInt(params.get("pagesize"));
			 } 
			 if (params.containsKey("username")) {
			       
				username = params.get("username");
			 } 
			 
		}
		System.out.println("username--->"+username);
		customerPage = customerService.getAllCustomerActivePageWiseAndAgent(pagenumber, pagesize,username);
		if(customerPage.getTotalElements()==0){
			 throw new CustomerNotFoundException();
		}
		HttpHeaders header  = new HttpHeaders();
		header.set("X-Total-Count", String.valueOf(customerPage.getTotalElements()));
		return ResponseEntity.ok().headers(header).body(customerPage);
	}
	
	@PutMapping("/updatepassword/{username}")
	public ResponseEntity<String> updatePassword(@PathVariable(name = "username") String username,@RequestBody UpdatePassword updatePasswordData) {

	    String response = customerService.updatePasswordByUsername(username, updatePasswordData);
	    return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
	
	

