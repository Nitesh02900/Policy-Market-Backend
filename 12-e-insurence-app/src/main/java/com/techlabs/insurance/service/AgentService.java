package com.techlabs.insurance.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entity.Agent;
import com.techlabs.insurance.payload.AgentDto;
import com.techlabs.insurance.payload.GetAgentDto;
import com.techlabs.insurance.payload.UpdateCustomer;

public interface AgentService {

	String addAgent(AgentDto agentDto);
	 List<GetAgentDto> findAllAgent();
	 Page<GetAgentDto> getAllAgentPageWise(int pageNumber , int pageSize);
	 Agent findAgentByUsername(String username);
	 String updateAgentDetailsByUsername(String username, UpdateCustomer updateAgentData);
}

