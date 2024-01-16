package com.techlabs.insurance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entity.Role;


public interface RoleRepository extends JpaRepository<Role,Integer >{

	Optional<Role> findByRolename(String name);
}

