package com.andre.adidas.codechallenge.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.andre.adidas.codechallenge.auth.JwtAuthenticationEntryPoint;
import com.andre.adidas.codechallenge.auth.JwtAuthenticationTokenFilter;

/**
 * Secutiry configuration
 * @author André da Silva Medeiros
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new UserDetailsServiceImpl();
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsServiceBean())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/login",
                        "/test/**",
                        "/**/*.html",
                        "/**/*.{png,jpg,jpeg,svg.ico}",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/v1/auth/**", "/**/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.GET, "/**/swagger-resources/**", "/**/webjars/springfox-swagger-ui/**", "/**/v2/api-docs/**").permitAll()
                .antMatchers(HttpMethod.POST,"/test/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated();
//
//        httpSecurity
//                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.headers().cacheControl();
    }
}
