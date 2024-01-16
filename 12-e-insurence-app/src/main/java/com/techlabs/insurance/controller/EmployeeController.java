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

import com.techlabs.insurance.entity.Admin;
import com.techlabs.insurance.entity.Employee;
import com.techlabs.insurance.exception.CustomerNotFoundException;
import com.techlabs.insurance.payload.EmployeeDto;
import com.techlabs.insurance.payload.GetCustomerDto;
import com.techlabs.insurance.payload.GetEmployeeDto;
import com.techlabs.insurance.payload.UpdateCustomer;
import com.techlabs.insurance.service.EmployeeService;


@RestController
@RequestMapping("/insuranceapp")
public class EmployeeController {
	
	@Autowired  
	private EmployeeService employeeService;
	
//	@PreAuthorize("hasRole('ADMIN')")/
	@PostMapping("/employee")
	public  ResponseEntity<String> AddEmployee(@RequestBody EmployeeDto employeedto)
	{
	
//		System.out.println(employee);
		String response= employeeService.addEmployee(employeedto);
		 return new ResponseEntity<>(response,HttpStatus.CREATED); 
	}
	
	@GetMapping("/employees")
	public ResponseEntity<Page<GetEmployeeDto>> getAllEmployee(@RequestParam Map<String,String>params)
	{
		Page<GetEmployeeDto> employeePage = null;
		int pagenumber =0;
		int pagesize = 30;
	
		if(params.isEmpty())
		{
			employeeService.findAllEmployee();
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
	
		employeePage = employeeService.getAllEmployeePageWise(pagenumber, pagesize);
			
		if(employeePage.getTotalElements()==0)
		{
			 throw new CustomerNotFoundException();
		}
		HttpHeaders header  = new HttpHeaders();
		header.set("X-Total-Count", String.valueOf(employeePage.getTotalElements()));
		return ResponseEntity.ok().headers(header).body(employeePage);
		
	}
	
	@GetMapping("/employee")
	 public ResponseEntity<Employee> getAdminByUserName(@RequestParam String username)
	 {
		 Employee employee = employeeService.findEmployeeByUsername(username);
		 if(employee==null)
		 {
			 return ResponseEntity.notFound().build();
		 }
		 else
		 {
			 return ResponseEntity.ok(employee);
		 }
	 }
	@PutMapping("/employee/{username}/{status}")
	public  ResponseEntity<String> UpdateEmployee( @PathVariable(name = "username") String username,@PathVariable(name = "status") String status)
	{
		String response = employeeService.UpadateEmployeeStatus(username, status);
		return new ResponseEntity<>(response,HttpStatus.CREATED); 
	}

	@PutMapping("/updateemployee/{username}")
	public ResponseEntity<String> updateCustomerDetails( @PathVariable(name = "username") String username, @RequestBody UpdateCustomer updateEmployeeData) {
		 System.out.println("Received UpdateCustomer payload: " + updateEmployeeData.toString());
	    String response = employeeService.updateEmployeeDetailsByUsername(username, updateEmployeeData);
	    return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}

