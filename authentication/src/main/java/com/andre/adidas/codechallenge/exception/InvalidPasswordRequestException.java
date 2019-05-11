package com.andre.adidas.codechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class InvalidPasswordRequestException extends RuntimeException {
	private static final long serialVersionUID = 3462134541771941311L;

	public InvalidPasswordRequestException() {
		super();
	}

	public InvalidPasswordRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPasswordRequestException(String message) {
		super(message);
	}

	public InvalidPasswordRequestException(Throwable cause) {
		super(cause);
	}
}
