package com.andre.adidas.codechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author andr3medeiros
 * https://github.com/andr3medeiros
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Um erro ocorreu ao tentar validar o usuário")
public class UserAuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 5829479937705514005L;

	public UserAuthenticationException() {
		super();
	}

	public UserAuthenticationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserAuthenticationException(String message) {
		super(message);
	}

	public UserAuthenticationException(Throwable cause) {
		super(cause);
	}
}
