package com.andre.adidas.codechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.andre.adidas.codechallenge")
public class ItinerarieCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItinerarieCalculatorApplication.class, args);
	}
}
