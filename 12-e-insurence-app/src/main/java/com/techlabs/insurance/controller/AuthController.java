package com.techlabs.insurance.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entity.Admin;
import com.techlabs.insurance.entity.User;
import com.techlabs.insurance.entity.UserDetails;
import com.techlabs.insurance.payload.JwtAuthResponse;
import com.techlabs.insurance.payload.LoginDto;
import com.techlabs.insurance.payload.UpdateCustomer;
import com.techlabs.insurance.repository.UserDetailsRepository;
import com.techlabs.insurance.service.AdminService;
import com.techlabs.insurance.service.AuthService;
import com.techlabs.insurance.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/insuranceapp") 
@RequiredArgsConstructor 
@CrossOrigin(value="*") 
public class AuthController {

	@Autowired
	private AuthService authService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserDetailsRepository userDetailsRepo;
	

//	 @PostMapping(value = {"/login" , "/sign"}) 
//	 public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) 
//	 { 
//	  String token = authService.login(loginDto); 
//	   
//	  JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(); 
//	  jwtAuthResponse.setAccessToken(token); 
//	   
//	  return ResponseEntity.ok(jwtAuthResponse); 
//	 } 
	
	
	 @PostMapping(value = {"/login" , "/sign"}) 
	 public ResponseEntity<User> login(@RequestBody LoginDto loginDto) 
	 { 
		 
		 String Username = loginDto.getUsername();
		 UserDetails userdetails = userDetailsRepo.findByUsernameAndActive(Username, true);
		 User user =null;
		  String token;
		  HttpHeaders headers = new HttpHeaders();
		 if(userdetails!=null)
		 {
			 token = authService.login(loginDto); 
			  JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(); 
			  jwtAuthResponse.setAccessToken(token); 
			 
			  headers.set("Bearer-Token",  token); 
//			  System.out.println("token in login---->"+token+"*");
			 
		 }
		 else
		 {
			 token = "Bad credentials";
		 }
		 if(!"Bad credentials".equals(token))
			{
					  user = userService.findUserByName(loginDto.getUsername());
					  return ResponseEntity.ok().headers(headers).body(user);
				  }
			 
			  
		
		 
	  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	 } 

	 
	 @PostMapping("/registeradmin") 
	 public ResponseEntity<String> registerAdmin(@RequestBody Admin admin) 
	 { 
		 System.out.println(admin);
		 String response = authService.registerAdmin(admin); 
		 return new ResponseEntity<>(response,HttpStatus.CREATED); 
	 } 
	 
	 
	 @GetMapping("/validate")  
	 public ResponseEntity<String> validateuser(@RequestHeader("Authorization") String authorizationHeader) {  
		 	String jwtToken = authorizationHeader.substring(7);  
	        System.out.println("JWT Token: " + jwtToken);  
	        String[] jwtParts = jwtToken.split("\\."); 
	        String encodedPayload = jwtParts[1]; 
	        byte[] decodedPayload = Base64.getDecoder().decode(encodedPayload);
	        String payload = new String(decodedPayload, StandardCharsets.UTF_8);
	        return ResponseEntity.ok(payload); 
	 }
	 
	 @GetMapping("/admin")
	 public ResponseEntity<Admin> getAdminByUserName(@RequestParam String username)
	 {
		 Admin admin = adminService.findAdminByUsername(username);
		 if(admin==null)
		 {
			 return ResponseEntity.notFound().build();
		 }
		 else
		 {
			 return ResponseEntity.ok(admin);
		 }
	 }
	 @PutMapping("/updateadmin/{username}") public ResponseEntity<String> updateCustomerDetails( @PathVariable(name = "username") String username,@RequestBody UpdateCustomer updateAdminData) {
			 System.out.println("Received UpdateCustomer payload: " + updateAdminData.toString());
		    String response = adminService.updateAdminDetailsByUsername(username, updateAdminData);
		    return new ResponseEntity<>(response, HttpStatus.CREATED);
		}
	 
	 
}

