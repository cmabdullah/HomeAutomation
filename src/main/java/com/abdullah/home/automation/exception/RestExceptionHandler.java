package com.abdullah.home.automation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = ApiError.class)
	public ResponseEntity<?> handleGenericNotFoundException(ApiError error) {

		ApiErrorResponse errorResponse = new ApiErrorResponse(error.getCode(), error.getErrors());

		return new ResponseEntity<>(errorResponse, error.getStatus());
	}	

}
//@ExceptionHandler(value = CustomerNotFoundException.class)
//@ResponseBody
//public ResponseEntity<?> handleGenericNotFoundException(CustomerNotFoundException e) {
//	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//}