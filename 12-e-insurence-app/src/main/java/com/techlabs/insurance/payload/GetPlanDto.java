package com.techlabs.insurance.payload;

import java.util.List;

import com.techlabs.insurance.entity.InsuranceScheme;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class GetPlanDto {

	private String planName;
	
    private boolean active;
}
