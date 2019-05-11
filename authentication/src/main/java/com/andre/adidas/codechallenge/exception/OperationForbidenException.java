package com.andre.adidas.codechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author andr3medeiros
 * https://github.com/andr3medeiros
 */
@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class OperationForbidenException extends RuntimeException {
	private static final long serialVersionUID = -5700627976725775691L;

	public OperationForbidenException() {
		super();
	}

	public OperationForbidenException(String message, Throwable cause) {
		super(message, cause);
	}

	public OperationForbidenException(String message) {
		super(message);
	}

	public OperationForbidenException(Throwable cause) {
		super(cause);
	}

	
}
