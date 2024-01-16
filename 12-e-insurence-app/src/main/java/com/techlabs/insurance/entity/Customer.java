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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

//    @NotBlank(message = "Address can't be empty")
    @Size(min = 3, max = 70)
    @Column
    private String address;

//    @NotBlank(message = "State can't be empty")
    @Size(min = 3, max = 30)
    @Column
    private String state;

//    @NotBlank(message = "City can't be empty")
    @Size(min = 3, max = 20)
    @Column
    private String city;

//    @NotBlank(message = "PinCode can't be empty")
    @Size(min = 3, max = 6)
    @Column
    private String pincode;

   
    @Column
    private boolean active;

    @Column
    private String documentStatus;
    
  
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "userDetailId")
    private UserDetails userdetails;

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", address=" + address + ", state=" + state + ", city=" + city
				+ ", pincode=" + pincode + ", active=" + active + ", documentStatus=" + documentStatus
				+ ", userdetails=" + userdetails + "]";
	}
    

//    @JsonManagedReference
//    @OneToMany(mappedBy = "customer", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    private List<Nominee> nominees;

    
    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Policy> policies;
    
    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Message> message;
    
 
	
}

