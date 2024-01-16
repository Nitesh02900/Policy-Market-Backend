package com.techlabs.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entity.InsurancePlan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface InsurancePlanRepository extends JpaRepository<InsurancePlan,Integer>{

	Page<InsurancePlan> findPlanByActive(boolean active, Pageable pageable); 
}
