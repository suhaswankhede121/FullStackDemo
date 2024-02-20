package com.taskmgnt.service;

import com.taskmgnt.model.User;

public interface UserService {

	public User getUserProfile(String jwt);
}
