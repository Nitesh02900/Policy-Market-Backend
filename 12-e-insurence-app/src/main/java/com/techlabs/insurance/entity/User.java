package com.techlabs.insurance.entity;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class User {

	@Column
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;
	
//	@Pattern(regexp="^[a-zA-Z]+( [a-zA-Z]+)*$" , message = "Name Contain only charater!")
//	@Size(min=3, max=50)
	@Column
	private String username;
	
//	@NotBlank(message= "Password can't be empty")
//	@Size(min=5, max=15, message="Password not less then 5 character!")
	@Column
	private String password;
	
	@ManyToMany(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinTable(joinColumns = @JoinColumn(name ="userId"), inverseJoinColumns = @JoinColumn(name="rollId"))
	private List<Role> roles;


	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", roles=" + roles
				+ "]";
	}
	
	
}
