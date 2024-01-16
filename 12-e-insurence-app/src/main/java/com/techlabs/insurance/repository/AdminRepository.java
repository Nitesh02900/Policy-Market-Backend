package com.techlabs.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin,Integer>{

//	public Admin findByUserId(String UserName);
//	public Admin findByPassword(String Password);
}
