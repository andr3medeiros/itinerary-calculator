package com.andre.adidas.codechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Senha inválida")
public class InvalidPasswordException extends RuntimeException {
	private static final long serialVersionUID = 960411498933766259L;
	
	public InvalidPasswordException() {
	}
	
	public InvalidPasswordException(String message) {
		super(message);
	}
}

