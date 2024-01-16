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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import com.techlabs.insurance.entity.Agent;
import com.techlabs.insurance.entity.Customer;
import com.techlabs.insurance.entity.Policy;
import com.techlabs.insurance.entity.Role;
import com.techlabs.insurance.entity.User;
import com.techlabs.insurance.entity.UserDetails;
import com.techlabs.insurance.exception.CustomerNotFoundException;
import com.techlabs.insurance.exception.UserAPIException;
import com.techlabs.insurance.payload.CustomerDto;
import com.techlabs.insurance.payload.GetCustomerDto;
import com.techlabs.insurance.payload.UpdateCustomer;
import com.techlabs.insurance.payload.UpdatePassword;
import com.techlabs.insurance.repository.AgentRepository;
import com.techlabs.insurance.repository.CustomerRepository;
import com.techlabs.insurance.repository.PolicyRepository;
import com.techlabs.insurance.repository.RoleRepository;
import com.techlabs.insurance.repository.UserDetailsRepository;
import com.techlabs.insurance.repository.UserRepository;



@Service
public class CustomerServiceImpl implements CustomerService{


	@Autowired
	private CustomerRepository customerRepo;
	 @Autowired
	private UserRepository userRepository;
	@Autowired
	private EntityManager entityManager;
	 
	 @Autowired
	 private UserDetailsRepository userDetailsRepo;
	 
	 @Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PolicyRepository policyRepository;
	@Autowired
	private AgentRepository agentRepo;
	
	 @Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String addCustomer(Customer customer) {
		customer.setActive(true);
		customer.getUserdetails().setActive(true);
		System.out.println(customer);
		if(userRepository.existsByUsername(customer.getUserdetails().getUsername())) 
			   throw new UserAPIException("User Already Exists",HttpStatus.BAD_REQUEST); 
		
		User user = new User();
		user.setUsername(customer.getUserdetails().getUsername());
		user.setPassword(passwordEncoder.encode(customer.getUserdetails().getPassword()));
		customer.getUserdetails().setPassword(passwordEncoder.encode(customer.getUserdetails().getPassword()));
		List<Role> roles = new ArrayList<Role>(); 
		   
		  Role userRole = roleRepository.findByRolename("ROLE_CUSTOMER").get(); 
		  roles.add(userRole); 
		  user.setRoles(roles); 
		  System.out.println(user);
		  userRepository.save(user);
		
		customerRepo.save(customer);
		return "User Registered Successfully"; 
		
	}
	
		public Customer findCustomerByUsername(String username) {
		UserDetails userDetails = userDetailsRepo.findByUsername(username);
		List<Customer> customers = customerRepo.findAll();
		for (Customer customer : customers) {
            if (customer.getUserdetails().getUserDetailId()== userDetails.getUserDetailId()) 
            {
               return customer;
            }
        }
		return null;
	}

		@Override
		public Page<GetCustomerDto> getAllCustomerPageWise(int pageNumber, int pageSize) {
		
			Pageable pageable = PageRequest.of(pageNumber, pageSize);
			Page<Customer> customerPage = customerRepo.findAll( pageable);
		
			List<GetCustomerDto> dtos = customerPage.getContent()
		            .stream()
		            .map(this::mapToDto)
		            .collect(Collectors.toList());

		        return new PageImpl<>(dtos, pageable, customerPage.getTotalElements());
		}
		
		@Override
		public Page<GetCustomerDto> getAllCustomerActivePageWise(int pageNumber, int pageSize) {
		
			Pageable pageable = PageRequest.of(pageNumber, pageSize);
			Page<Customer> customerPage = customerRepo.findCustomerByActive(true, pageable);
		
			List<GetCustomerDto> dtos = customerPage.getContent()
		            .stream()
		            .map(this::mapToDto)
		            .collect(Collectors.toList());

		        return new PageImpl<>(dtos, pageable, customerPage.getTotalElements());
		}
		
		private GetCustomerDto mapToDto(Customer customer) {
	        GetCustomerDto dto = new GetCustomerDto();
	        
	        dto.setFirstname(customer.getUserdetails().getFirstname());
	        dto.setLastname(customer.getUserdetails().getLastname());
	        dto.setUsername(customer.getUserdetails().getUsername());
	        dto.setEmailId(customer.getUserdetails().getEmailId());
	        dto.setMobileNumber(customer.getUserdetails().getMobileNumber());
	        dto.setActive(customer.isActive());
	        dto.setDateOfBirth(customer.getUserdetails().getDateOfBirth());
	        return dto;
	    }

		@Override
		public List<GetCustomerDto> findAllCustomer() {
			List<Customer> customers = customerRepo.findAll();
	        return customers.stream()
	            .map(this::mapToDto)
	            .collect(Collectors.toList());
		}

		@Override
		public String DeleteCustomer(String username) {
			
			UserDetails userDetails  = userDetailsRepo.findByUsername(username);
			List<Customer> customers= customerRepo.findAll();
			for(Customer customer:customers)
			{
				if(customer.getUserdetails().getUserDetailId()==userDetails.getUserDetailId())
				{
					customer.setActive(false);
					return "Customer is deleted";
				}
			}
			return "Customer Doesn't exist";
		}

		@Override
		public String UpadateCustomerStatus(String username, String status) {
			UserDetails userDetails  = userDetailsRepo.findByUsername(username);
			List<Customer> customers= customerRepo.findAll();
			for(Customer customer:customers)
			{
				if(customer.getUserdetails().getUserDetailId()==userDetails.getUserDetailId())
				{
					if("True".equals(status))
					{
						customer.setActive(true);
						userDetails.setActive(true);
						customerRepo.save(customer);
						return "Status updated true";
					}
					else
					{
						customer.setActive(false);
						userDetails.setActive(false);
						customerRepo.save(customer);
						return "Status updated false";
					}
				}
			}
			return "Customer Doesn't exist";
		}

		@Override
		public Customer UpdateCustomerDetails(CustomerDto customerRegisterDetails) {
			// TODO Auto-generated method stub
			return null;
		}
		
//		@Override
//		public String updateCustomerData( UpdateCustomer updatedCustomerDetails) {
//		    UserDetails userDetails = userDetailsRepo.findByUsername(updatedCustomerDetails.getUsername());
//		    List<Customer> customers = customerRepo.findAll();
//		    
//		    for (Customer customer : customers) {
//		        if (customer.getUserdetails().getUserDetailId() == userDetails.getUserDetailId()) {
//		 
//		        	customer.setAddress(updatedCustomerDetails.getAddress());
//		        	customer.setCity(updatedCustomerDetails.getCity());
//		        	customer.setPincode(updatedCustomerDetails.getPincode());
//		        	customer.setState(updatedCustomerDetails.getState());
//		            customer.getUserdetails().setFirstname(updatedCustomerDetails.getFirstname());
//		            customer.getUserdetails().setLastname(updatedCustomerDetails.getLastname());
//		            customer.getUserdetails().setEmailId(updatedCustomerDetails.getEmail());
//		            customer.getUserdetails().setMobileNumber(updatedCustomerDetails.getMobileNumber());
//		            customer.getUserdetails().setDateOfBirth(updatedCustomerDetails.getDateOfBirth());
//		           
//		            customerRepo.save(customer);
//
//		            return "Customer details updated successfully";
//		        }
//		    }
//		    
//		    return "Customer doesn't exist";
//		}

		
		@Transactional
		@Override
		public String updateCustomerDetailsByUsername(String username, UpdateCustomer updateCustomerData) {
		    Customer existingCustomer = findCustomerByUsername(username);

		    if (existingCustomer == null) {
		        throw new CustomerNotFoundException();
		    }

		    UserDetails userDetails = existingCustomer.getUserdetails();
		    System.out.println("Before Update - Firstname: " + userDetails.getFirstname());
		    System.out.println("Before Update - Lastname: " + userDetails.getLastname());
		    System.out.println("Before Update - Email: " + userDetails.getEmailId());
		    System.out.println("Before Update - Address: " + existingCustomer.getAddress());
		    System.out.println("Before Update - Phone: " + userDetails.getMobileNumber());
		    System.out.println("Before Update - City: " + existingCustomer.getCity());
		    System.out.println("Before Update - Pincode: " + existingCustomer.getPincode());

		    // Update first name, last name, and email
		    if (updateCustomerData.getFirstname() != null) {
		        userDetails.setFirstname(updateCustomerData.getFirstname());
		    }

		    if (updateCustomerData.getLastname() != null) {
		        userDetails.setLastname(updateCustomerData.getLastname());
		    }

		    if (updateCustomerData.getEmailId() != null) {
		        System.out.println("Email from UpdateCustomer: " + updateCustomerData.getEmailId());
		        userDetails.setEmailId(updateCustomerData.getEmailId());
		        entityManager.merge(userDetails);
		    }

		    // Update address, phone, city, and pin code
		    if (updateCustomerData.getAddress() != null) {
		        existingCustomer.setAddress(updateCustomerData.getAddress());
		    }

		    if (updateCustomerData.getMobileNumber() != null) {
		        userDetails.setMobileNumber(updateCustomerData.getMobileNumber());
		    }

		    if (updateCustomerData.getCity() != null) {
		        existingCustomer.setCity(updateCustomerData.getCity());
		    }

		    if (updateCustomerData.getPincode() != null) {
		        existingCustomer.setPincode(updateCustomerData.getPincode());
		    }

		    // Debugging: Print values after updating
		    System.out.println("After Update - Firstname: " + userDetails.getFirstname());
		    System.out.println("After Update - Lastname: " + userDetails.getLastname());
		    System.out.println("After Update - Email: " + userDetails.getEmailId());
		    System.out.println("After Update - Address: " + existingCustomer.getAddress());
		    System.out.println("After Update - Phone: " + userDetails.getMobileNumber());
		    System.out.println("After Update - City: " + existingCustomer.getCity());
		    System.out.println("After Update - Pincode: " + existingCustomer.getPincode());

		    userDetailsRepo.save(userDetails);
		    customerRepo.save(existingCustomer);

		    return "User details updated successfully";
		}
		
		@Override
		public String updateCustomerRegisterDetails(CustomerDto customerDto) {
			UserDetails userDetails  = userDetailsRepo.findByUsername(customerDto.getUsername());
			List<Customer> customers= customerRepo.findAll();
			for(Customer customer:customers)
			{
				if(customer.getUserdetails().getUserDetailId()==userDetails.getUserDetailId())
				{
					customer.setActive(true);
					customer.setAddress(customerDto.getAddress());
					customer.setCity(customerDto.getCity());
					customer.setState(customerDto.getState());
					customer.setPincode(customerDto.getPincode());
					userDetails.setMobileNumber(customerDto.getMobileNumber());
					userDetails.setDateOfBirth(customerDto.getDateOfBirth());
					customerRepo.save(customer);
					userDetailsRepo.save(userDetails);
					return "Customer Data is saved";
				}
			}
			
			
			
			return "No customer is found";
		}

		@Override
		public Page<GetCustomerDto> getAllCustomerActivePageWiseAndAgent(int pageNumber, int pageSize,String username) {
			
			Pageable pageable = PageRequest.of(pageNumber, pageSize);
			List<Policy>policies = policyRepository.findAll();
			UserDetails agentUserDetails = userDetailsRepo.findByUsername(username);
//			System.out.println("agentUserDetails------247--------->"+agentUserDetails);
//			System.out.println(agentUserDetails.getUsername());
			List<Agent> agents = agentRepo.findAll();
			Agent agentdetails=null;
			for (Agent agent : agents) {
	            if (agent.getUserdetails().getUserDetailId()== agentUserDetails.getUserDetailId()) 
	            {
	            	agentdetails = agent;
//	            	System.out.println("agentDetails---- line255--------->"+agentdetails);
	            	 break;
	             
	            }
	        }
			List<Customer> customers= new ArrayList<>();
			for (Policy policy:policies)
			{
				
				if(policy.getAgent()!=null && policy.getAgent().getAgentId()==agentdetails.getAgentId())
				{
					
					customers.add(policy.getCustomer());
				}
			}
			 List<GetCustomerDto> customerDtos = customers.stream()
			            .map(this::mapToDto)
			            .collect(Collectors.toList());

			    int start = (int) pageable.getOffset();
			    int end = Math.min((start + pageable.getPageSize()), customerDtos.size());
			    Page<GetCustomerDto> page = new PageImpl<>(customerDtos.subList(start, end), pageable, customerDtos.size());

			    return page;
		
		
		}

//		@Override
//		public String updatePasswordByUsername(String username, UpdatePassword updatePasswordData) {
//			UserDetails userDetails = userDetailsRepo.findByUsername(username);
//
//	        if (userDetails != null) {
//	            // Implement your logic to validate and update the password
//	            userDetails.setPassword(updatePasswordData.getNewPassword());
//	            userDetailsRepo.save(userDetails);
//
//	            return "Password updated successfully";
//	        } else {
//	            return "User not found";
//	        }
//		}
//		@Override
//		public String updatePasswordByUsername(String username, UpdatePassword updatePasswordData) {
//		    UserDetails userDetails = userDetailsRepo.findByUsername(username);
//
//		    if (userDetails != null) {
//		       
//		        //userDetails.setPassword(passwordEncoder.encode(updatePasswordData.getNewPassword()));
//		    	userDetails.setPassword(updatePasswordData.getNewPassword());
//
//		        userDetailsRepo.save(userDetails);
//
//		    
//		        Optional<User> userOptional = userRepository.findByUsername(username);
//		        if (userOptional.isPresent()) {
//		            User user = userOptional.get();
//		            user.setPassword(updatePasswordData.getNewPassword());
//		            userRepository.save(user);
//		        } else {
//		            return "User not found in User table";
//		        }
//
//		        return "Password updated successfully";
//		    } else {
//		        return "User not found in UserDetails table";
//		    }
//		}
		
		@Override
		public String updatePasswordByUsername(String username, UpdatePassword updatePasswordData) {
		    UserDetails userDetails = userDetailsRepo.findByUsername(username);

		    if (userDetails != null) {
		       
		        userDetails.setPassword(updatePasswordData.getNewPassword());
		        userDetailsRepo.save(userDetails);

		    
		        Optional<User> userOptional = userRepository.findByUsername(username);
		        if (userOptional.isPresent()) {
		            User user = userOptional.get();
		            user.setPassword(passwordEncoder.encode(updatePasswordData.getNewPassword()));
		            userRepository.save(user);
		        } else {
		            return "User not found in User table";
		        }

		        return "Password updated successfully";
		    } else {
		        return "User not found in UserDetails table";
		    }
		}
		
}
