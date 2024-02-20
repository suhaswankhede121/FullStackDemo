package com.taskmgnt.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoResponse {

	private String jwt;
	private String message;
	private Boolean status;
}
