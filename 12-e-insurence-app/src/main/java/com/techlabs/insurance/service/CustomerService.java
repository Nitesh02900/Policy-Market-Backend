package com.techlabs.insurance.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entity.Customer;
import com.techlabs.insurance.payload.CustomerDto;
import com.techlabs.insurance.payload.GetCustomerDto;
import com.techlabs.insurance.payload.UpdateCustomer;
import com.techlabs.insurance.payload.UpdatePassword;

public interface CustomerService {

	String addCustomer(Customer customer);
	Customer findCustomerByUsername(String username);
	Customer UpdateCustomerDetails(CustomerDto customerRegisterDetails);
	Page<GetCustomerDto> getAllCustomerPageWise(int pageNumber, int pageSize);
	Page<GetCustomerDto> getAllCustomerActivePageWise(int pageNumber, int pageSize);
	List<GetCustomerDto> findAllCustomer();
	String DeleteCustomer(String username);
	String UpadateCustomerStatus(String username,String status );
//	String updateCustomerData( UpdateCustomer updatedCustomerDetails);
	String updateCustomerDetailsByUsername(String username, UpdateCustomer updateCustomerData);
	String updateCustomerRegisterDetails( CustomerDto customerDto);
	
	Page<GetCustomerDto> getAllCustomerActivePageWiseAndAgent(int pageNumber, int pageSize,String username);
	
	String updatePasswordByUsername(String username, UpdatePassword updatePasswordData);
}
