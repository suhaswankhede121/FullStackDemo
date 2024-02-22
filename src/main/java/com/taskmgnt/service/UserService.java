package com.taskmgnt.service;

import java.util.List;

import com.taskmgnt.model.User;

public interface UserService {

	public User getUserProfile(String jwt);
	public List<User> getAllUsers();
}
