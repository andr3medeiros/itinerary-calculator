package com.andre.adidas.codechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.andre.adidas.codechallenge.security.GatewayJwtAuthenticationTokenFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	public GatewayJwtAuthenticationTokenFilter gatewayJwtAuthenticationTokenFilterBean() {
		return new GatewayJwtAuthenticationTokenFilter();
	}
}
