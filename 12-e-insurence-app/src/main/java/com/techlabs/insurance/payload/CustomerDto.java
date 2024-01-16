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
public class CustomerDto {

	private String username;
	private String mobileNumber;
	private Date dateOfBirth;
	 private String address;
	 private String state;
	 private String city;
	 private String pincode;
	
}
