package com.techlabs.insurance.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class InsurancePlan {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planId;
	
	@Column
	@Pattern(regexp="^[a-zA-Z]+( [a-zA-Z]+)*$" , message = "Name Contain only character!")
	@NotBlank(message= "Plan Name can't be empty")
	private String planName;
	
	@Column
//	@Pattern(regexp="^[a-zA-Z]+( [a-zA-Z]+)*$" , message = "Name Contain only character!")
//	@NotBlank(message= "Plan Name can't be empty")
	private String planDetails;
	
	@Column
    private boolean active;
	
//	@JsonManagedReference
//	@OneToMany(mappedBy = "insuranceplan")
//	private List<InsuranceScheme> schemes;
	
	
	  @JsonManagedReference
	  @JsonIgnore
	  @OneToMany(mappedBy = "plan", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	  private List<InsuranceScheme> schemes;

	@Override
	public String toString() {
		return "InsurancePlan [planId=" + planId + ", planName=" + planName + ", planDetails=" + planDetails
				+ ", active=" + active + ", schemes=" + schemes + "]";
	}
	  
	  
}
