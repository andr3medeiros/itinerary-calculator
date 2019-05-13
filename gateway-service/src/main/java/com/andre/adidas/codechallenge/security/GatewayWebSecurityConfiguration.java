package com.andre.adidas.codechallenge.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Secutiry configuration
 * @author André da Silva Medeiros
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(101)
public class GatewayWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private GatewayJwtAuthenticationTokenFilter gatewayJwtAuthenticationTokenFilter;
	
	/*
	 * @Override
	 * 
	 * @Bean public AuthenticationManager authenticationManagerBean() throws
	 * Exception { return super.authenticationManagerBean(); }
	 */
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsServiceBean())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity
        .csrf().disable()
        .cors()
        .and()
        .authorizeRequests()
        .antMatchers(
                HttpMethod.GET,
                "/",
                "/signin",
                "/test/**",
                "/**/*.html",
                "/**/*.{png,jpg,jpeg,svg.ico}",
                "/**/*.css",
                "/**/*.js"
        ).permitAll()
        .antMatchers("/**/swagger-ui.html").permitAll()
        .antMatchers(HttpMethod.GET, "/**/swagger-resources/**", "/**/webjars/springfox-swagger-ui/**", "/**/v2/api-docs/**").permitAll()
        .antMatchers(HttpMethod.OPTIONS).permitAll()
        .antMatchers(HttpMethod.POST, "/authentication/v1/auth/**").permitAll()
        .anyRequest().authenticated();
    	
    	httpSecurity.exceptionHandling().authenticationEntryPoint((req, resp, e) -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
			    	.and()
			        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			        .and()
			        .addFilterAfter(gatewayJwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}