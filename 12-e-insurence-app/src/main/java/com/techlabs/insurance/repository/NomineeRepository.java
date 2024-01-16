package com.techlabs.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entity.Nominee;


public interface NomineeRepository extends JpaRepository<Nominee, Integer>{

}
