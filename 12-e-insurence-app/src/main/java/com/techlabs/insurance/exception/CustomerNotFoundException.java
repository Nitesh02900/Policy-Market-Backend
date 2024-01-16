package com.techlabs.insurance.exception;

public class CustomerNotFoundException extends RuntimeException{

	public CustomerNotFoundException()
	{
		
	}
	public String getMessage()
	{
		return "Customer not found";
	}
}
