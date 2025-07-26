package com.library.exception;

import org.springframework.http.HttpStatus;

public class LibraryServiceNoDataFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String summary;
	private final String description;

	private final HttpStatus status;
	private final String errorCode;

	public LibraryServiceNoDataFoundException(String message, String summary, String description) {
		super(message);
		this.summary = summary;
		this.description = description;
		this.status = HttpStatus.NOT_FOUND;
		this.errorCode = "ERR_NOT_FOUND";
	}

	public String getSummary() {
		return summary;
	}

	public String getDescription() {
		return description;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
