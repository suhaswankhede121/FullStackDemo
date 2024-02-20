package com.taskmgnt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmgnt.config.JwtProvider;
import com.taskmgnt.model.User;
import com.taskmgnt.repository.UserRepository;
import com.taskmgnt.request.LoginRequest;
import com.taskmgnt.response.AutoResponse;
import com.taskmgnt.service.UserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserServiceImpl userServiceImple;
	
	
	@PostMapping("/signup")
	public ResponseEntity<AutoResponse> createUserHandler(@RequestBody User user) throws Exception{
		
		String email=user.getEmail();
		String password=user.getPassword();
		String fullName=user.getFullName();
		String role=user.getRole();
		
		User isEmailExit=userRepository.findByEmail(email);
		
		if(isEmailExit!=null) {
			throw new Exception("User is already login");
		}
		
		User createdUser=new User();
		createdUser.setEmail(email);
		createdUser.setFullName(fullName);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setRole(role);
		
		User savedUser=userRepository.save(createdUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		String token=JwtProvider.generateToken(authentication);
		
		AutoResponse authResponse=new AutoResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Register Succesfully");
		authResponse.setStatus(true);
		
		
		return new ResponseEntity<>(authResponse,HttpStatus.OK);
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AutoResponse> signin(@RequestBody LoginRequest request){
		String username=request.getEmail();
		String password=request.getPassword();
		
		System.out.println(username+"......."+password);
		Authentication authentication=authenticate(username,password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token=JwtProvider.generateToken(authentication);
		
		AutoResponse authResponse=new AutoResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Login Sucessfully");
		return new ResponseEntity<>(authResponse,HttpStatus.OK);
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails=userServiceImple.loadUserByUsername(username);
		
		System.out.println("Sign in with "+userDetails);
		
		if(userDetails==null) {
			System.out.println("Sign in userdetails is null"+userDetails);
			throw new BadCredentialsException("Invalid username and password");
			
		}
		
		if(!passwordEncoder.matches(password,userDetails.getPassword())) {
			System.out.println("Password not match"+userDetails);
			throw new BadCredentialsException("Wrong password");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
	
	
}
