package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entity.Employee;
import com.techlabs.insurance.entity.Role;
import com.techlabs.insurance.entity.User;
import com.techlabs.insurance.entity.UserDetails;
import com.techlabs.insurance.exception.EmployeeNotFoundException;
import com.techlabs.insurance.exception.UserAPIException;
import com.techlabs.insurance.payload.EmployeeDto;
import com.techlabs.insurance.payload.GetEmployeeDto;
import com.techlabs.insurance.payload.UpdateCustomer;
import com.techlabs.insurance.repository.EmployeeRepository;
import com.techlabs.insurance.repository.RoleRepository;
import com.techlabs.insurance.repository.UserDetailsRepository;
import com.techlabs.insurance.repository.UserRepository;


@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepo;
	 @Autowired
	private UserRepository userRepository;
	 @Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserDetailsRepository userDetailsRepo;
	
	 @Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public String addEmployee(EmployeeDto employeedto) {
		Employee employee = new Employee();
		employee.setActive(true);
		employee.setSalary(employeedto.getSalary());
		System.out.println(employeedto);
		if(userRepository.existsByUsername(employeedto.getUsername())) 
			   throw new UserAPIException("User Already Exists",HttpStatus.BAD_REQUEST); 
		User user = new User();
		user.setUsername(employeedto.getUsername() );
		user.setPassword(passwordEncoder.encode(employeedto.getPassword()));
		List<Role> roles = new ArrayList<Role>(); 
		
		   
		  Role userRole = roleRepository.findByRolename("ROLE_EMPLOYEE").get(); 
		  roles.add(userRole); 
		  user.setRoles(roles); 
		  System.out.println(user);
		  userRepository.save(user);
		  UserDetails userdetails = new UserDetails();
		  userdetails.setActive(true);
		  userdetails.setFirstname(employeedto.getFirstname());
		  userdetails.setLastname(employeedto.getLastname());
		  userdetails.setUsername(employeedto.getUsername());
//		  userdetails.setPassword(passwordEncoder.encode(employeedto.getPassword()));
		  userdetails.setPassword(employeedto.getPassword());
		  userdetails.setEmailId(employeedto.getEmailId());
		  userdetails.setMobileNumber(employeedto.getMobileNumber());
		  userdetails.setDateOfBirth(employeedto.getDateOfBirth());
		  
		  
		  employee.setUserdetails(userdetails);
		
		employeeRepo.save(employee);
		return "Empolyee Registered Successfully"; 
		
	}
	
	
	@Override
	public Page<GetEmployeeDto> getAllEmployeePageWise(int pageNumber, int pageSize) {
	
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Employee> employeePage = employeeRepo.findAll(pageable);
		List<GetEmployeeDto> dtos = employeePage.getContent()
	            .stream()
	            .map(this::mapToDto)
	            .collect(Collectors.toList());

	        return new PageImpl<>(dtos, pageable, employeePage.getTotalElements());
	}
	
	private GetEmployeeDto mapToDto(Employee employee) {
	    return new GetEmployeeDto(
	            employee.getUserdetails().getFirstname(),
	            employee.getUserdetails().getLastname(),
	            employee.getUserdetails().getUsername(),
	            employee.getUserdetails().getEmailId(),
	            employee.getSalary(),
	            employee.getUserdetails().getMobileNumber(),
	            employee.getUserdetails().getDateOfBirth(),
	            employee.isActive()
	    );
	}
	
	@Override
	public List<GetEmployeeDto> findAllEmployee() {
		List<Employee> employees = employeeRepo.findAll();
        return employees.stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
	}
	
	
	


	@Override
	public Employee findEmployeeByUsername(String username) {
		UserDetails userDetails = userDetailsRepo.findByUsername(username);
		List<Employee> employees = employeeRepo.findAll();
		for(Employee employee:employees)
		{
			if (employee.getUserdetails().getUserDetailId()== userDetails.getUserDetailId()) 
            {
               return employee;
            }
		}
		return null;
	}


	@Override
	public String UpadateEmployeeStatus(String username, String status) {
		UserDetails userDetails  = userDetailsRepo.findByUsername(username);
		List<Employee> employees= employeeRepo.findAll();
		for(Employee employee:employees)
		{
			if(employee.getUserdetails().getUserDetailId()==userDetails.getUserDetailId())
			{
				if("True".equals(status))
				{
					employee.setActive(true);
					userDetails.setActive(true);
					employeeRepo.save(employee);
					return "Status updated true";
				}
				else
				{
					employee.setActive(false);
					userDetails.setActive(false);
					employeeRepo.save(employee);
					return "Status updated false";
				}
			}
		}
		return "Employee Doesn't exist";
	}
	
	@Override
	public String updateEmployeeDetailsByUsername(String username, UpdateCustomer updateEmployeeData) {
		 Employee existingCustomer = findEmployeeByUsername(username);

		    if (existingCustomer == null) {
		        throw new EmployeeNotFoundException();
		    }

		    UserDetails userDetails = existingCustomer.getUserdetails();

		   
		    System.out.println("Before Update - Firstname: " + userDetails.getFirstname());
		    System.out.println("Before Update - Lastname: " + userDetails.getLastname());
		    System.out.println("Before Update - Email: " + userDetails.getEmailId());

		   
		    if (updateEmployeeData.getFirstname() != null) {
		        userDetails.setFirstname(updateEmployeeData.getFirstname());
		    }

		    if (updateEmployeeData.getLastname() != null) {
		        userDetails.setLastname(updateEmployeeData.getLastname());
		    }

		    if (updateEmployeeData.getEmailId() != null) {
		    	System.out.println("Email from UpdateCustomer: " + updateEmployeeData.getEmailId());

		        userDetails.setEmailId(updateEmployeeData.getEmailId());
		       // entityManager.merge(userDetails);
		    }

		   
		    System.out.println("After Update - Firstname: " + userDetails.getFirstname());
		    System.out.println("After Update - Lastname: " + userDetails.getLastname());
		    System.out.println("After Update - Email: " + userDetails.getEmailId());

		    userDetailsRepo.save(userDetails);

		    return "User details updated successfully";
	}
}
