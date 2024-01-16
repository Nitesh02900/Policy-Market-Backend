package com.techlabs.insurance.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class MessageDto {

	private long Id;
	private String question;
	private String answer;
	private String status;
	private String firstName;
	private String lastName;
}
