package com.techlabs.insurance.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entity.Admin;
import com.techlabs.insurance.entity.UserDetails;
import com.techlabs.insurance.exception.AdminNotFoundException;
import com.techlabs.insurance.payload.UpdateCustomer;
import com.techlabs.insurance.repository.AdminRepository;
import com.techlabs.insurance.repository.RoleRepository;
import com.techlabs.insurance.repository.UserDetailsRepository;
import com.techlabs.insurance.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepo;

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	 private UserDetailsRepository userDetailsRepo;
	
	public AdminServiceImpl(AdminRepository adminRepo, UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	

	@Override
	public void addAdmin(Admin admin) {

		adminRepo.save(admin);
	}
	
	public Admin findAdminByUsername(String username)
	{
		UserDetails userDetails = userDetailsRepo.findByUsername(username);
		List<Admin> admins = adminRepo.findAll();
		for (Admin admin : admins) {
            if (admin.getUserdetails().getUserDetailId()== userDetails.getUserDetailId()) 
            {
               return admin;
            }
        }
		return null;
		
	}
	

	@Override
	public String updateAdminDetailsByUsername(String username, UpdateCustomer updateAdminData) {
		 Admin existingCustomer = findAdminByUsername(username);

		    if (existingCustomer == null) {
		        throw new AdminNotFoundException();
		    }

		    UserDetails userDetails = existingCustomer.getUserdetails();

		   
		    System.out.println("Before Update - Firstname: " + userDetails.getFirstname());
		    System.out.println("Before Update - Lastname: " + userDetails.getLastname());
		    System.out.println("Before Update - Email: " + userDetails.getEmailId());

		   
		    if (updateAdminData.getFirstname() != null) {
		        userDetails.setFirstname(updateAdminData.getFirstname());
		    }

		    if (updateAdminData.getLastname() != null) {
		        userDetails.setLastname(updateAdminData.getLastname());
		    }

		    if (updateAdminData.getEmailId() != null) {
		    	System.out.println("Email from UpdateCustomer: " + updateAdminData.getEmailId());

		        userDetails.setEmailId(updateAdminData.getEmailId());
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
