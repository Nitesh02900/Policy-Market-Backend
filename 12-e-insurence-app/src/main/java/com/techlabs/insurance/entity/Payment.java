package com.techlabs.insurance.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.techlabs.insurance.enums.PaymentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
public class Payment {

	
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int paymentId;
	
	@Column 
	private int installmentNo ;
	
	@Column 
	private long CardNumber ;
	
	@Column 
	private double amount;
	
	
	
	
	@Column 
	private String nameOnCard;
	
	@Column
	private Date date;
	@Column
	private String status;
	
	
	@JsonBackReference
	 @ManyToOne
	 @JoinColumn(name = "policyNumber") // The column name that represents the relationship in the database
	 private Policy policy;
	
	
	
	 
	
	
}
