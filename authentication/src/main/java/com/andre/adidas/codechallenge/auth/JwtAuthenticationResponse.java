package com.andre.adidas.codechallenge.auth;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {
	private static final long serialVersionUID = -7284707602053812231L;
	
	private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    @Override
    public String toString() {
        return String.format("{\"token\":\"%s\"}", this.token);
    }

}
