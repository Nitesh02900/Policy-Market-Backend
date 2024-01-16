package com.techlabs.insurance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class Nominee {

	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int nomineeId;

	 	
//	 	@Pattern(regexp="^[a-zA-Z]+( [a-zA-Z]+)*$" , message = "Name Contain only character!")
//	    @NotBlank(message = "Name can't be empty")
//	 	@Size(min=3, max=50)
	    @Column
	    private String name;

//	 	@Pattern(regexp="^[a-zA-Z]+( [a-zA-Z]+)*$" , message = "Name Contain only character!")
//	    @NotBlank(message = "Nominee Relation can't be empty")
//	 	@Size(min=3, max=50)
	    @Column
	    private String nomineeRelation;

	 	@JsonBackReference
		 @ManyToOne
		 @JoinColumn(name = "policyNumber") // The column name that represents the relationship in the database
		 private Policy policy;

	    // Getters and setters...

	    @Override
	    public String toString() {
	        return "Nominee [nomineeId=" + nomineeId + ", name=" + name + ", nomineeRelation=" + nomineeRelation + "]";
	    }
	
}
