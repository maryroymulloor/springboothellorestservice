package com.sample.springboot.hello.exceptions;

public class UserAlreadyExistsException extends Exception{

	public static final long serialVersionUID=12345;

	public UserAlreadyExistsException(String message) {
		super(message);
	}
	
}
