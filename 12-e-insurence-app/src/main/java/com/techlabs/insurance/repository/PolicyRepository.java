package com.techlabs.insurance.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entity.Agent;
import com.techlabs.insurance.entity.Customer;
import com.techlabs.insurance.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy,String>{
	
	Policy findByPolicyNumber(int policyNumber);
	Page<Policy> findByCustomer(Customer customerdetails, Pageable pageable);
	Page<Policy> findByAgent(Agent agentdetails, Pageable pageable);
	Policy findByPolicyNumberAndActive(int policyNumber, boolean active);
	long countByCustomer(Customer customer);

}
