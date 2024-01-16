package com.techlabs.insurance.message;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ResponseFile {

	 private String name;
	  private String url;
	  private String type;
	  private long size;
}
