package com.techlabs.insurance.exception;

public class PlanNotFoundException extends RuntimeException{

	public PlanNotFoundException()
	{
		
	}
	public String getMessage()
	{
		return "Plan not found";
	}
}