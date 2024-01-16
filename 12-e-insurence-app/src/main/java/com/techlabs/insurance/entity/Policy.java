package com.techlabs.insurance.entity;

import java.util.Date;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Policy {
	
	

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int policyNumber;
	

	@Column
//	@NotBlank(message= "Issue Date can't be empty")
	private Date issueDate;
	
//	@NotBlank(message= "Maturity Date can't be empty")
	@Column
	private Date maturityDate;
	
//	@NotBlank(message= "Premimum Type can't be empty")
	@Column
	private int premiumType;		//time period //3month
	
	@Column
	private int numberOfYear;
	
//	@NotBlank(message= "Total Premimum can't be empty")
	@Column 
	private double totalPremiumAmount;   // total amount to be paid //50000
	
	
//	@NotBlank(message= "Profit Ratio can't be empty")
	@Column 
	private double profitRatio; //4%
	
//	@NotBlank(message= "Installment Amount can't be empty")
	@Column
	private double installmentAmount;   // on the basis of time period //4100
	
//	@NotBlank(message= "Intrest Amount can't be empty")
	@Column
	private double intrestAmount; //6000
	
//	@NotBlank(message= "Maturity Benefit can't be empty")
	@Column 
	private double maturityBenefit;  //amount receive after insurance policy reaches its term //506000
	
	@Column
	private boolean active;
	
	@Column
	private boolean status;
	
	
	@ManyToOne
	@JoinColumn(name = "customerId") 
	@JsonBackReference
	@JsonIgnore
	private Customer customer;
	
	
	@ManyToOne
	@JoinColumn(name = "agentId") 
	@JsonBackReference
	@JsonIgnore
	private Agent agent;
	
	
//	 @ManyToOne
//	 @JoinColumn(name = "SchemeId")
//	 @JsonBackReference
//	 private InsuranceScheme scheme;
	 
	 @JsonBackReference
	 @ManyToOne
	 @JoinColumn(name = "schemeId") // The column name that represents the relationship in the database
	 @JsonIgnore
	 private InsuranceScheme scheme;
	 
	 
//	 @JsonManagedReference
//	 @OneToMany(mappedBy = "Policy", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//	 private List<Nominee> Nominees;
	 
	 @JsonManagedReference
	 @JsonIgnore
	 @OneToMany(mappedBy = "policy", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	 private List<Nominee> Nominees;
	
	 @JsonManagedReference
	 @JsonIgnore
	 @OneToMany(mappedBy = "policy", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	 private List<Payment> payments;
	 
	 @JsonManagedReference
	 @OneToMany(mappedBy = "policy",  cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	 private  List<FileDB> fileDB;
	 
	 @Override
		public String toString() {
			return "Policy [policyNumber=" + policyNumber + ", issueDate=" + issueDate + ", maturityDate=" + maturityDate
					+ ", premiumType=" + premiumType + ", numberOfYear=" + numberOfYear + ", totalPremiumAmount="
					+ totalPremiumAmount + ", profitRatio=" + profitRatio + ", installmentAmount=" + installmentAmount
					+ ", intrestAmount=" + intrestAmount + ", maturityBenefit=" + maturityBenefit + ", active=" + active
					+ ", customer=" + customer + ", agent=" + agent + ", scheme=" + scheme + ", Nominees=" + Nominees
					+ ", payments=" + payments + ", fileDB=" + fileDB + "]";
		}
	  
	  
}
