package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

	public static final String SERVER_ERROR_CODE = "SERVER_ERROR";
	public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
	public static final String ILLEGAL_STATE = "ILLEGAL_STATE";

	private String code;
	private String title;
	private String detail;

	public ApiError(String code, Throwable ex) {
		this(code, ex.getMessage(), ex.getMessage());
	}
}
