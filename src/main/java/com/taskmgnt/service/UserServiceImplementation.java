package com.taskmgnt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmgnt.config.JwtProvider;
import com.taskmgnt.model.User;
import com.taskmgnt.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User getUserProfile(String jwt) {
		
		String email=JwtProvider.getEmailFromJwtToken(jwt);
		return userRepository.findByEmail(email);
	}

}
