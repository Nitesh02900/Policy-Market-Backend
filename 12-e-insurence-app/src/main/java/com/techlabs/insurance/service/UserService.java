package com.techlabs.insurance.service;

import com.techlabs.insurance.entity.User;

public interface UserService {

	User findUserByName(String username);
}
