package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entity.Admin;
import com.techlabs.insurance.entity.Role;
import com.techlabs.insurance.entity.User;
import com.techlabs.insurance.exception.UserAPIException;
import com.techlabs.insurance.payload.LoginDto;
import com.techlabs.insurance.repository.RoleRepository;
import com.techlabs.insurance.repository.UserRepository;
import com.techlabs.insurance.security.JwtTokenProvider;


@Service
public class AuthServiceImpl implements AuthService{

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private JwtTokenProvider jwtTokenProvider;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminService adminService;
	
	
	
	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.passwordEncoder = passwordEncoder;
	}
	@Override
	public String login(LoginDto loginDto) {
		try
		{
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
			System.out.println("authentication---->" +authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtTokenProvider.generateToken(authentication);
			System.out.println("token is "+token);
			return token;
		}
		catch (BadCredentialsException e)
		{
			System.out.println("bad");
			return e.getMessage();
		}
		
	}

	@Override
	public String registerAdmin(Admin admin) {
		System.out.println(admin);
		if(userRepository.existsByUsername(admin.getUserdetails().getUsername())) 
			   throw new UserAPIException("User Already Exists",HttpStatus.BAD_REQUEST); 
		User user = new User();
		user.setUsername(admin.getUserdetails().getUsername());
		user.setPassword(passwordEncoder.encode(admin.getUserdetails().getPassword()));
		List<Role> roles = new ArrayList<Role>(); 
		   
		  Role userRole = roleRepository.findByRolename("ROLE_ADMIN").get(); 
		  roles.add(userRole); 
		  user.setRoles(roles); 
		   
		  userRepository.save(user); 
		  adminService.addAdmin(admin);
		  System.out.println(user); 
		    
		  return "User Registered Successfully"; 
		
	}

}
