package com.techlabs.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entity.Agent;


public interface AgentRepository extends JpaRepository<Agent, Integer>{

}