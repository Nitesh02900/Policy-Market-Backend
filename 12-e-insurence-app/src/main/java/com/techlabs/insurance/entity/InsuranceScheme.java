package com.techlabs.insurance.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class InsuranceScheme {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int schemeId;
	
//	@Pattern(regexp="^[a-zA-Z]+( [a-zA-Z]+)*$" , message = "Name Contain only character!")
//	@NotBlank(message= "Scheme Name can't be empty")
	@Column
	private String schemeName;
	
//	@NotBlank(message= "Minimum Amount can't be empty")
	@Column
	private int minAmount ;
	
//	@NotBlank(message= "Maximum Amount can't be empty")
	@Column
	private int maxAmount;
	
//	@NotBlank(message= "Minimum Age can't be empty")
	@Column 
	private int minAge;
	
//	@NotBlank(message= "Maximum Age can't be empty")
	@Column
	private int maxAge;
	
//	@NotBlank(message= "Minimum Time can't be empty")
	@Column
	private int  minTime;
	
//	@NotBlank(message= "Maximum Time can't be empty")
	@Column
	private int maxTime;
	
//	@NotBlank(message= "ProfitRatio can't be empty")
	@Column
	private double profitRatio;
	
	@Column
	private double commission;
	
	@Column
    private boolean active;

	

	 @ManyToOne
	 @JoinColumn(name = "planId")
	 @JsonBackReference
	 private InsurancePlan plan;
	  
	 @JsonManagedReference
	 @JsonIgnore
	 @OneToMany(mappedBy = "scheme", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	 private List<Policy> policies;
	  

    
}

