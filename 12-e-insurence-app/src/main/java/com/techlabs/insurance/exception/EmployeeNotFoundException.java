package com.techlabs.insurance.exception;

public class EmployeeNotFoundException extends RuntimeException{

	public EmployeeNotFoundException()
	{
		
	}
	public String getMessage()
	{
		return "Employee not found";
	}
}
