package com.example.demo;

public class InactiveAccountException extends RuntimeException {

	public InactiveAccountException(String message) {
		super(message);
	}

	public InactiveAccountException(String message, Throwable cause) {
		super(message, cause);
	}
}
