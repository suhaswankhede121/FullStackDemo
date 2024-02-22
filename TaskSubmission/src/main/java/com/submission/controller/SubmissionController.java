package com.submission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.submission.model.Submission;
import com.submission.model.UserDto;
import com.submission.service.SubmissionService;
import com.submission.service.TaskService;
import com.submission.service.UserService;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {
	
	@Autowired
	private SubmissionService submissionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping()
	public ResponseEntity<Submission> submitTask(@RequestParam Long taskId,@RequestParam String gitHubLink,@RequestHeader("Authorization")String jwt) throws Exception{
		UserDto user=userService.getUserProfile(jwt);
		Submission submission=submissionService.submitTask(taskId, gitHubLink,user.getId(), jwt);
		return new ResponseEntity<>(submission,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Submission> getSubmissionById(@RequestParam Long id,@RequestHeader("Authorization")String jwt) throws Exception{
		UserDto user=userService.getUserProfile(jwt);
		Submission submission=submissionService.getTaskSubmissionById(id);
		return new ResponseEntity<>(submission,HttpStatus.CREATED);
	}
	
	@GetMapping()
	public ResponseEntity<List<Submission>> getAllSubmissions(@RequestHeader("Authorization")String jwt) throws Exception{
		UserDto user=userService.getUserProfile(jwt);
		List<Submission> submission=submissionService.getAllSubmissions();
		return new ResponseEntity<>(submission,HttpStatus.CREATED);
	}
	
	@GetMapping("/task/{taskid}")
	public ResponseEntity<List<Submission>> getAllTaskSubmission(@RequestParam Long id,@RequestHeader("Authorization")String jwt) throws Exception{
		UserDto user=userService.getUserProfile(jwt);
		List<Submission> submission=submissionService.getTaskSubmissionsByTaskId(id);
		return new ResponseEntity<>(submission,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Submission> acceptOrDeclineSubmission(@RequestParam Long id,@RequestParam("status") String status,@RequestHeader("Authorization")String jwt) throws Exception{
		UserDto user=userService.getUserProfile(jwt);
		Submission submission=submissionService.acceptDeclineSubmission(id, status);
		return new ResponseEntity<>(submission,HttpStatus.CREATED);
	}	
	
	
	
}
