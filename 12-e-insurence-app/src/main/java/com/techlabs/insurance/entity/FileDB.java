package com.techlabs.insurance.entity;


import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
public class FileDB {
	
	   @Id
	  @GeneratedValue(generator = "uuid")
	  @GenericGenerator(name = "uuid", strategy = "uuid2")
	  private String id;

//	  @NotBlank(message = "Name can't be empty")
//	  @Size(min = 3, max = 20)
	  @Column
	  private String name;

//	  @NotBlank(message = "Type can't be empty")
//	  @Size(min = 3, max = 30)
	  @Column
	  private String type;

	  @Lob
	  @Column(name = "documentfile",length=2000000)
	  private byte[] data;
	  
	  public FileDB(String name, String type, byte[] data) {
		    this.name = name;
		    this.type = type;
		    this.data = data;
		  }
	  
		@JsonBackReference
		 @ManyToOne
		 @JoinColumn(name = "policyNumber") // The column name that represents the relationship in the database
		 private Policy policy;

	 

}
