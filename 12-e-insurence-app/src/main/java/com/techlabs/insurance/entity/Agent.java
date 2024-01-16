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
public class Agent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int agentId;

//    @NotBlank(message= "Total Commission can't be empty")
    @Column
    private double totalCommission;
    
  
    @Column
    private boolean active;
    
    
    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "agent", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Policy> policies;
    

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "userDetailId")
    private UserDetails userdetails;


//	@Override
//	public String toString() {
//		return "Agent [agentId=" + agentId + ", totalCommission=" + totalCommission + ", active=" + active
//				+ ", policies=" + policies + ", userdetails=" + userdetails + "]";
//	}
    
    
  
}
