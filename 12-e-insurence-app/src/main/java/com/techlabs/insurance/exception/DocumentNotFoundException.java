package com.techlabs.insurance.exception;

public class DocumentNotFoundException extends RuntimeException{

	public DocumentNotFoundException()
	{
		
	}
	public String getMessage()
	{
		return "Document not found";
	}
}
