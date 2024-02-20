package com.taskmgnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmgnt.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

	public User findByEmail(String email);
}
