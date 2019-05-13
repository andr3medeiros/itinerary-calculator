package com.andre.adidas.codechallenge.docs;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author André da Silva Medeiros
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    public SwaggerConfig() {
        super("com.andre.adidas.codechallenge.controller");
    }
}
