package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class UserAPIException extends RuntimeException{

	
	private String message;
	private HttpStatus status;
	public UserAPIException(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}
	@Override
	public String getMessage() {
		return "UserAPIException [message=" + message + ", status=" + status + "]";
	}
}
