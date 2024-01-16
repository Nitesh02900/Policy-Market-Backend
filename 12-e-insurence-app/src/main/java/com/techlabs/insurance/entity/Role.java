package com.techlabs.insurance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class Role {

	
	@Column
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int roleId;
	
	@Pattern(regexp="^[a-zA-Z]+( [a-zA-Z]+)*$" , message = "Name Contain only charater!")
	@Size(min=3, max=50)
	@Column
	private String rolename;
}
