package com.techlabs.insurance.exception;

public class AgentNotFoundException extends RuntimeException{

	public AgentNotFoundException()
	{
		
	}
	public String getMessage()
	{
		return "Agent not found";
	}
}
