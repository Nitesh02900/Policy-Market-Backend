package com.techlabs.insurance.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entity.User;
import com.techlabs.insurance.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	
	@Autowired
	UserRepository userRepository;
	@Override
	public User findUserByName(String username) {
		Optional<User> users = userRepository.findByUsername(username);
		User user=null;
		if(users.isPresent())
		{
			user =users.get();
			
		}
		return user;
	}
}
