package com.techlabs.insurance.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class UserDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userDetailId;
	
//	@Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z]+)*$", message = "Name must contain only characters")
//    @NotBlank(message = "FirstName can't be empty")
//    @Size(min = 3, max = 50)
    @Column
    private String firstname;

//    @Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z]+)*$", message = "Name must contain only characters")
//    @NotBlank(message = "LastName can't be empty")
//    @Size(min = 3, max = 50)
    @Column
    private String lastname;
    
    
//    @NotBlank(message = "UserId can't be empty")
//    @Size(min = 3, max = 50)
    @Column(unique = true)
    private String username;

    //@NotBlank(message = "Password can't be empty")
//    @Size(min = 5, max = 15, message = "Password must be at least 5 characters long")
    @Column
    private String password;

//    @Pattern(regexp="[a-zA-Z0-9_+&*#-]+[@][a-z]+[.][a-z]{2,3}" , message = "Invalid Email")
//	@NotBlank(message= "Email can't be empty")
    @Column
    private String emailId;

	//@Size(min=10, max=10, message="Mobile no not less then or more than 10 numbers!")
    @Column
    private String mobileNumber;
    
    @Column
    private Date dateOfBirth;
    
    @Column
    private boolean active;
	@Override
	public String toString() {
		return "UserDetails [userDetailId=" + userDetailId + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", username=" + username + ", password=" + password + ", emailId=" + emailId + ", mobileNumber="
				+ mobileNumber + ", dateOfBirth=" + dateOfBirth + ", active=" + active + "]";
	}
    
   
}

