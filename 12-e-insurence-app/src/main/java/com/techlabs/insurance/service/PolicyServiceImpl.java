package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entity.Agent;
import com.techlabs.insurance.entity.Customer;
import com.techlabs.insurance.entity.FileDB;
import com.techlabs.insurance.entity.Policy;
import com.techlabs.insurance.entity.UserDetails;
import com.techlabs.insurance.exception.DocumentNotFoundException;
import com.techlabs.insurance.exception.PolicyNotFoundException;
import com.techlabs.insurance.payload.DocumentDto;
import com.techlabs.insurance.payload.GetCustomerDto;
import com.techlabs.insurance.payload.PolicyDataDto;
import com.techlabs.insurance.repository.AgentRepository;
import com.techlabs.insurance.repository.CustomerRepository;
import com.techlabs.insurance.repository.PolicyRepository;
import com.techlabs.insurance.repository.UserDetailsRepository;


@Service
public class PolicyServiceImpl implements PolicyService{

	
	@Autowired
	PolicyRepository policyRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	 private UserDetailsRepository userDetailsRepo;
	@Autowired
	 private AgentRepository agentRepo;
	
	
	@Override
	public List<Policy> getAllPolicy() {
		List<Policy> policies = policyRepo.findAll();
	    
	    return policies;
	}

	 
	@Override
	public Page<PolicyDataDto> getAllPolicyPageWise(int pageNumber, int pageSize) {
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);
	    Page<Policy> policyPage = policyRepo.findAll(pageable);
	    return policyPage.map(this::convertToPolicyDataDto);
	}

	private PolicyDataDto convertToPolicyDataDto(Policy policy) {
	    // Perform the conversion from Policy to PolicyDataDto here
	    return new PolicyDataDto(
	    		policy.getPolicyNumber(),
	    		policy.getCustomer().getUserdetails().getFirstname(),

	            policy.getScheme().getPlan().getPlanName(),

	            policy.getScheme().getSchemeName(),
	    		 policy.getNumberOfYear(),
	    		 policy.getIssueDate(),
	    		 
	    		policy.getTotalPremiumAmount(),
	            policy.getInstallmentAmount(),
	            
	            policy.getPremiumType(),
	           
	            policy.getScheme().getCommission(),
	            policy.isStatus() ? true: false,
	            policy.isActive() ? true: false
	           );
	}
	
	@Override
	public List<DocumentDto> getalldocument(int policynumber) {
		Policy policy= policyRepo.findByPolicyNumber(policynumber);

		List<DocumentDto> documentDtos=new ArrayList<>();
		
		
		List<FileDB> documents=policy.getFileDB();
		if(documents.size()==0) {
			throw new DocumentNotFoundException();
		}
			
			for(FileDB document:documents) {
				DocumentDto documentDto=new DocumentDto();
				documentDto.setDocumentname(document.getName());
				documentDto.setDocumentFile(document.getData());
				
				documentDtos.add(documentDto);
			}
		
		
		return documentDtos;
		
	}


//	@Override
//	public Page<PolicyDataDto> getAllPolicyPageWiseAndUsername(int pageNumber, int pageSize, String username) {
//		 Pageable pageable = PageRequest.of(pageNumber, pageSize);
//		 System.out.println("username=----->" +username);
//		 UserDetails userDetails = userDetailsRepo.findByUsername(username);
//			List<Customer> customers = customerRepo.findAll();
//			Customer customerdetails=null;
//			for (Customer customer : customers) {
//	            if (customer.getUserdetails().getUserDetailId()== userDetails.getUserDetailId()) 
//	            {
//	              customerdetails = customer;
//	            }
//	        }
//			List<Policy> policies =new ArrayList<>();
//			List<Policy> Allpolicies = policyRepo.findAll();
//			for(Policy policy :Allpolicies)
//			{
//				if(policy.getCustomer().getCustomerId()==customerdetails.getCustomerId()) {
//					policies.add(policy);
//				}
//				
//			}
//			System.out.println("policy sise"+policies.size());
//			Page<Policy> policyPage = new PageImpl<>(policies, pageable, policies.size());
//			 System.out.println("total no of policy1"+policyPage.getTotalElements());
//		    Page<PolicyDataDto> policyDataDtoPage = policyPage.map(PolicyDataDto::fromPolicy);
//		    System.out.println("total no of policy2"+policyDataDtoPage.getTotalElements());
//		    return policyDataDtoPage;
//	}

	
	
	@Override
	public Page<PolicyDataDto> getAllPolicyPageWiseAndUsername(int pageNumber, int pageSize, String username) {
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);
	    System.out.println("username=----->" + username);
	    UserDetails userDetails = userDetailsRepo.findByUsername(username);

	    List<Customer> customers = customerRepo.findAll();
	    Customer customerdetails = null;

	    for (Customer customer : customers) {
	        if (customer.getUserdetails().getUserDetailId() == userDetails.getUserDetailId()) {
	            customerdetails = customer;
	        }
	    }

	    Page<Policy> policyPage = policyRepo.findByCustomer(customerdetails,pageable);
	    System.out.println("policy size: " + policyPage.getTotalElements());

	    return policyPage.map(this::convertToPolicyDataDto);
		
	}


	@Override
	public String updatePolicyStatus(int policyNumber, String status) {
		Policy policy = policyRepo.findByPolicyNumber(policyNumber);
		if("True".equals(status))
		{
			policy.setStatus(true);
		}
		else
		{
			policy.setStatus(false);
		}
		policyRepo.save(policy);
		return "Status updated";
	}


	@Override
	public Page<PolicyDataDto> getAllPolicyByPageWiseAndAgent(int pageNumber, int pageSize, String username) {
		   Pageable pageable = PageRequest.of(pageNumber, pageSize);
		   UserDetails userDetails = userDetailsRepo.findByUsername(username);

		    List<Agent> agents = agentRepo.findAll();
		    Agent agentdetails = null;

		    for (Agent agent:agents) {
		        if (agent.getUserdetails().getUserDetailId() == userDetails.getUserDetailId()) {
		            agentdetails = agent;
		        }
		    }

		    Page<Policy> policyPage = policyRepo.findByAgent(agentdetails, pageable);
		    System.out.println("policy size: " + policyPage.getTotalElements());
		    return policyPage.map(this::convertToPolicyDataDto);
	
	}

}