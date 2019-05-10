package com.andre.adidas.codechallenge.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class  JwtAuthenticationRequest implements Serializable {
	private static final long serialVersionUID = 820323728127302803L;
	private String organizationName;
    private String username;
    private String email;
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String organization, String username, String email, String password) {
        this.setOrganizationName(organization);
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
    }
}
