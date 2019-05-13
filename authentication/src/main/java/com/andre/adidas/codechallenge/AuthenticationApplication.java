package com.andre.adidas.codechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.andre.adidas.codechallenge.services.UserDetailsServiceImpl;

@SpringBootApplication
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}
	
    @Bean
    public UserDetailsService userDetailsServiceBean() {
        return new UserDetailsServiceImpl();
    }
}
