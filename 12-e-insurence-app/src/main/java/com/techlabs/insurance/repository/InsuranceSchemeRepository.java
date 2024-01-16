package com.techlabs.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entity.InsuranceScheme;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface InsuranceSchemeRepository extends JpaRepository<InsuranceScheme,Integer>{

	Page<InsuranceScheme> findSchemeByActive(boolean active, Pageable pageable);
}
