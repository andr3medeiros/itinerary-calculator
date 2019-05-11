package com.andre.adidas.codechallenge.auth;

import java.io.Serializable;

import lombok.Data;

@Data
public class  JwtAuthenticationRequest implements Serializable {
	private static final long serialVersionUID = 820323728127302803L;
    private String username;
    private String email;
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String email, String password) {
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
    }
}
