package com.sample.springboot.hello.exceptions;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@Override
	//handle Method Argument Not Valid Exception thrown in create Users
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), 
				"fromMethodArgumentNotValid in GEH", 
				ex.getLocalizedMessage()); 
		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
	}
	
	//HttpRequestMethodNotSupported implementation
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), 
				"fromhandleHttpRequestMethodNotSupported in GEH - Method not allowed", 
				ex.getLocalizedMessage());
		return new ResponseEntity<>(customErrorDetails, HttpStatus.METHOD_NOT_ALLOWED);
	
	}
	
	@ExceptionHandler(UserNameNotFoundException.class)
	protected ResponseEntity<Object> handleUserNameNotFoundException(
			UserNameNotFoundException ex, WebRequest request) {

		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), 
				"fromhandleUserNameNotFoundException in GEH - UserName not found", 
				ex.getLocalizedMessage());
		return new ResponseEntity<>(customErrorDetails, HttpStatus.METHOD_NOT_ALLOWED);
	
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstrainViolationException(
			ConstraintViolationException ex, WebRequest request) {

		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), 
				"handleConstrainViolationException in GEH - UserName not found", 
				ex.getLocalizedMessage());
		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
	
	}
}
