package com.techlabs.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
