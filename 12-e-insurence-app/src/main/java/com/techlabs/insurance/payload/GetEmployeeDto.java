package com.techlabs.insurance.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class GetEmployeeDto {
	private String firstname;
	private String lastname;
	private String username;
	private String emailId;
	private double salary;
	private String mobileNumber;
	private Date dateOfBirth;
	private boolean active;

}