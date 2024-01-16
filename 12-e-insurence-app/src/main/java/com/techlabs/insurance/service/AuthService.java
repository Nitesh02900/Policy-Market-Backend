package com.techlabs.insurance.service;

import com.techlabs.insurance.entity.Admin;
import com.techlabs.insurance.payload.LoginDto;

public interface AuthService {

	String login(LoginDto loginDto);
	String registerAdmin(Admin admin);

}
