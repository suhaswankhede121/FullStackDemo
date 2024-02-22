package com.submission.service;

import java.util.List;

import com.submission.model.Submission;

public interface SubmissionService {

	Submission submitTask(Long taskId, String gitHubLink, Long userId,String jwt) throws Exception;
	Submission getTaskSubmissionById(Long submissionId) throws Exception;
	List<Submission> getAllSubmissions();
	List<Submission> getTaskSubmissionsByTaskId(Long taskId);
	Submission acceptDeclineSubmission(Long id, String status) throws Exception;
}
