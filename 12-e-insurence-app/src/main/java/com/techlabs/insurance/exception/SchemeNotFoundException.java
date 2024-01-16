package com.techlabs.insurance.exception;

public class SchemeNotFoundException extends RuntimeException{

	public SchemeNotFoundException()
	{
		
	}
	public String getMessage()
	{
		return "Scheme not found";
	}
}
