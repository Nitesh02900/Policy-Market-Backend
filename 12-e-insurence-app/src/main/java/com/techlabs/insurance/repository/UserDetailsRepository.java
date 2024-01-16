package com.techlabs.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entity.UserDetails;


public interface UserDetailsRepository extends JpaRepository<UserDetails,Integer >{
  UserDetails findByUsername(String Username);
  UserDetails findByUsernameAndActive(String username,boolean active);
}
