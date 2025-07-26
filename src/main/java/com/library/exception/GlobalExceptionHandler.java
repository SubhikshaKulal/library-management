package com.library.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(LibraryServiceNoDataFoundException.class)
	public ResponseEntity<Map<String, Object>> handleLibraryServiceNoDataFound(LibraryServiceNoDataFoundException ex) {
		logger.warn("LibraryServiceNoDataFoundException: {}", ex.getMessage());

		Map<String, Object> errorBody = new LinkedHashMap<>();
		errorBody.put("timestamp", LocalDateTime.now());
		errorBody.put("status", ex.getStatus().value());
		errorBody.put("error", ex.getStatus().getReasonPhrase());
		errorBody.put("message", ex.getMessage());
		errorBody.put("summary", ex.getSummary());
		errorBody.put("description", ex.getDescription());
		errorBody.put("errorCode", ex.getErrorCode());

		return new ResponseEntity<>(errorBody, ex.getStatus());
	}

	// Optional: generic fallback handler
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneralError(Exception ex) {
		logger.error("Unhandled exception occurred", ex);
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		body.put("error", "Internal Server Error");
		body.put("message", ex.getMessage());
		body.put("errorCode", "ERR_INTERNAL");

		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
