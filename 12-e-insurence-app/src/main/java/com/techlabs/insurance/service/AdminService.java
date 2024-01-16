package com.techlabs.insurance.service;

import com.techlabs.insurance.entity.Admin;
import com.techlabs.insurance.payload.UpdateCustomer;

public interface AdminService {

	void  addAdmin(Admin admin);
	Admin findAdminByUsername(String username);
	String updateAdminDetailsByUsername(String username, UpdateCustomer updateAdminData);
	
}