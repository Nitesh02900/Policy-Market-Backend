package com.techlabs.insurance.exception;

public class AdminNotFoundException extends RuntimeException{

	public AdminNotFoundException()
	{
		
	}
	public String getMessage()
	{
		return "Admin not found";
	}
}
