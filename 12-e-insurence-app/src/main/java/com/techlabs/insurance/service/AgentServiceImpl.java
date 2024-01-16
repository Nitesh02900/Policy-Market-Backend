package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import com.techlabs.insurance.entity.Agent;
import com.techlabs.insurance.entity.Role;
import com.techlabs.insurance.entity.User;
import com.techlabs.insurance.entity.UserDetails;
import com.techlabs.insurance.exception.AgentNotFoundException;
import com.techlabs.insurance.exception.UserAPIException;
import com.techlabs.insurance.payload.AgentDto;
import com.techlabs.insurance.payload.GetAgentDto;
import com.techlabs.insurance.payload.UpdateCustomer;
import com.techlabs.insurance.repository.AgentRepository;
import com.techlabs.insurance.repository.RoleRepository;
import com.techlabs.insurance.repository.UserDetailsRepository;
import com.techlabs.insurance.repository.UserRepository;



@Service
public class AgentServiceImpl  implements AgentService{

	@Autowired
	private AgentRepository agentRepo;
	 @Autowired
	private UserRepository userRepository;
	 @Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserDetailsRepository userDetailsRepo;
		
	
	 @Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String addAgent(AgentDto agentDto) {
		System.out.println("agentDto--->"+agentDto);
		Agent agent =  new Agent();
		agent.setActive(true);
		agent.setTotalCommission(agentDto.getTotalCommission());
		if(userRepository.existsByUsername(agentDto.getUsername())) 
			   throw new UserAPIException("User Already Exists",HttpStatus.BAD_REQUEST); 
		User user = new User();
		user.setUsername(agentDto.getUsername());
		user.setPassword(passwordEncoder.encode(agentDto.getPassword()));
		List<Role> roles = new ArrayList<Role>(); 
		   
		  Role userRole = roleRepository.findByRolename("ROLE_AGENT").get(); 
		  roles.add(userRole); 
		  user.setRoles(roles); 
		  System.out.println(user);
		  userRepository.save(user);
		  UserDetails userdetails = new UserDetails();
		  userdetails.setActive(true);
		  userdetails.setFirstname(agentDto.getFirstname());
		  userdetails.setLastname(agentDto.getLastname());
		  userdetails.setUsername(agentDto.getUsername());
//		  userdetails.setPassword(passwordEncoder.encode(agentDto.getPassword()));
		  userdetails.setPassword(agentDto.getPassword());
		  userdetails.setEmailId(agentDto.getEmailId());
		  userdetails.setMobileNumber(agentDto.getMobileNumber());
		  userdetails.setDateOfBirth(agentDto.getDateOfBirth());
		  System.out.println("userdetial------>"+userdetails);
		  agent.setUserdetails(userdetails);
		  System.out.println("agent---------->"+agent);
		
		agentRepo.save(agent);
		return "Agent Registered Successfully"; 
		
	}
	
	public Page<GetAgentDto> getAllAgentPageWise(int pageNumber , int pageSize)
	{
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Agent> agentPage = agentRepo.findAll(pageable);
		List<GetAgentDto> dtos = agentPage.getContent()
	            .stream()
	            .map(this::mapToDto)
	            .collect(Collectors.toList());
		  return new PageImpl<>(dtos, pageable, agentPage.getTotalElements());
	
	}
	
	private GetAgentDto mapToDto(Agent agent) {
	    return new GetAgentDto(
	    		agent.getUserdetails().getFirstname(),
	    		agent.getUserdetails().getLastname(),
		        agent.getUserdetails().getUsername(),
		        agent.getUserdetails().getEmailId(),
		        agent.getTotalCommission(),
			    agent.getUserdetails().getMobileNumber(),
			    agent.getUserdetails().getDateOfBirth(),
			    agent.isActive()
	    );
	}
	
	
	@Override
	public List<GetAgentDto> findAllAgent() {
		List<Agent> agents = agentRepo.findAll();
        return agents.stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
		
        
	}

	@Override
	public Agent findAgentByUsername(String username) {
		UserDetails userDetails = userDetailsRepo.findByUsername(username);
		List<Agent> agents = agentRepo.findAll();
		for(Agent agent:agents)
		{
			if (agent.getUserdetails().getUserDetailId()== userDetails.getUserDetailId()) 
            {
               return agent;
            }
		}
		
		return null;
	}
	
	@Override
	public String updateAgentDetailsByUsername(String username, UpdateCustomer updateAgentData) {
		 Agent existingAgent = findAgentByUsername(username);

		    if (existingAgent == null) {
		        throw new AgentNotFoundException();
		    }

		    UserDetails userDetails = existingAgent.getUserdetails();

		   
		    System.out.println("Before Update - Firstname: " + userDetails.getFirstname());
		    System.out.println("Before Update - Lastname: " + userDetails.getLastname());
		    System.out.println("Before Update - Email: " + userDetails.getEmailId());

		   
		    if (updateAgentData.getFirstname() != null) {
		        userDetails.setFirstname(updateAgentData.getFirstname());
		    }

		    if (updateAgentData.getLastname() != null) {
		        userDetails.setLastname(updateAgentData.getLastname());
		    }

		    if (updateAgentData.getEmailId() != null) {
		    	System.out.println("Email from UpdateCustomer: " + updateAgentData.getEmailId());

		        userDetails.setEmailId(updateAgentData.getEmailId());
		       // entityManager.merge(userDetails);
		    }

		    // Debugging: Print values after updating
		    System.out.println("After Update - Firstname: " + userDetails.getFirstname());
		    System.out.println("After Update - Lastname: " + userDetails.getLastname());
		    System.out.println("After Update - Email: " + userDetails.getEmailId());

		    userDetailsRepo.save(userDetails);

		    return "User details updated successfully";
	}
	
}
