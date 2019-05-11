package com.andre.adidas.codechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author andr3medeiros
 * https://github.com/andr3medeiros
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class UserNotFoundException extends AuthenticationException {
	private static final long serialVersionUID = -7273817444587206091L;

	public UserNotFoundException() {
		super("Usuário não encontrado");
	}

	public UserNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}

	public UserNotFoundException(String msg) {
		super(msg);
	}
}
