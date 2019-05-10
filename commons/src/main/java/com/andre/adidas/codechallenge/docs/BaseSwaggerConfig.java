package com.andre.adidas.codechallenge.docs;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author André da Silva Medeiros
 */
public class BaseSwaggerConfig {
    private final String basePackage;

    public BaseSwaggerConfig(String basePackage) {
        this.basePackage = basePackage;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Adidas Code Challenge using Spring Boot Microservices")
                .description("Candidate: André da Silva Medeiros")
                .version("1.0")
                .contact(new Contact("André da Silva Medeiros", "https://www.linkedin.com/in/andr3medeiros", "andr3medeiros@gmail.com"))
                .license("GNU")
                .build();
    }
}
