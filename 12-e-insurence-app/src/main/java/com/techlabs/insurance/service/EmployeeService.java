package com.techlabs.insurance.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entity.Employee;
import com.techlabs.insurance.payload.EmployeeDto;
import com.techlabs.insurance.payload.GetEmployeeDto;
import com.techlabs.insurance.payload.UpdateCustomer;

public interface EmployeeService {
	String addEmployee(EmployeeDto employeeDto);
	 Page<GetEmployeeDto> getAllEmployeePageWise(int pageNumber, int pageSize);
	 List<GetEmployeeDto> findAllEmployee();
	 Employee findEmployeeByUsername(String username);
	String UpadateEmployeeStatus(String username, String status);
	String updateEmployeeDetailsByUsername(String username, UpdateCustomer updateEmployeeData);
}
