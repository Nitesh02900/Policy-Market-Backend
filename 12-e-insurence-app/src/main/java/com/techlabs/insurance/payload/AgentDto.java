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
public class AgentDto {

	
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String emailId;
	private double totalCommission;
	private String mobileNumber;
	private Date dateOfBirth;
	@Override
	public String toString() {
		return "AgentDto [firstname=" + firstname + ", lastname=" + lastname + ", username=" + username + ", password="
				+ password + ", emailId=" + emailId + ", totalCommission=" + totalCommission + ", mobileNumber="
				+ mobileNumber + ", dateOfBirth=" + dateOfBirth + "]";
	}
}
