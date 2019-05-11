package com.andre.adidas.codechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author andr3medeiros
 * https://github.com/andr3medeiros
 */
@ResponseStatus(value= HttpStatus.CONFLICT, reason="User with this username or email already exists")
public class UserAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1636121607368435187L;
}
