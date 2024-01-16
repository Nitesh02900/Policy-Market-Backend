package com.techlabs.insurance.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
public class Employee {

	
	




	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;
    
//	@NotBlank(message = "Salary can't be empty")
    @Column
    private double salary;
    
    
    @Column
    private boolean active;
    
	    
	    

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "userDetailId")
    private UserDetails userdetails;
    
    @Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", salary=" + salary + ", active=" + active + ", userdetails="
				+ userdetails + "]";
	}
    
}

