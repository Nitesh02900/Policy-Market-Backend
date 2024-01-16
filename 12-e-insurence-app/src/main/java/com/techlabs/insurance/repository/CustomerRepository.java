package com.techlabs.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.techlabs.insurance.entity.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	List<Customer>findByActive(boolean active);
	Page<Customer>  findCustomerByActive(boolean active, Pageable pageable);
//	Customer findByUsernameAndActive(boolean active,String username);
	
}