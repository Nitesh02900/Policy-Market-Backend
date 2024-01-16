package com.techlabs.insurance.exception;

public class PolicyNotFoundException extends RuntimeException{

	public PolicyNotFoundException()
	{
		
	}
	public String getMessage()
	{
		return "Policy not found";
	}
}
