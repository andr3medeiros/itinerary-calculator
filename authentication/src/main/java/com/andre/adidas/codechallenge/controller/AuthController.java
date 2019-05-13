package com.andre.adidas.codechallenge.controller;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.andre.adidas.codechallenge.auth.JwtAuthenticationRequest;
import com.andre.adidas.codechallenge.auth.JwtAuthenticationResponse;
import com.andre.adidas.codechallenge.auth.JwtUser;
import com.andre.adidas.codechallenge.auth.JwtUtil;
import com.andre.adidas.codechallenge.entities.Role;
import com.andre.adidas.codechallenge.entities.User;
import com.andre.adidas.codechallenge.exception.InvalidPasswordException;
import com.andre.adidas.codechallenge.exception.UserAlreadyExistsException;
import com.andre.adidas.codechallenge.exception.UserAuthenticationException;
import com.andre.adidas.codechallenge.exception.UserNotFoundException;
import com.andre.adidas.codechallenge.services.RoleService;
import com.andre.adidas.codechallenge.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * AuthController provides signup, signin and token refresh methods
 * @author André da Silva Medeiros
 */
@RestController
@Slf4j
@Api(value = "Endpoints to manage authentication")
public class AuthController {

    @Value("${auth.header}")
    private String tokenHeader;

    public final static String SIGNUP_URL = "/v1/auth/signup";
    public final static String SIGNIN_URL = "/v1/auth/signin";
    public final static String REFRESH_TOKEN_URL = "/v1/auth/token/refresh";

    @Autowired
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public void setJwtTokenUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Adds new user and returns authentication token
     * @param authenticationRequest request with username, email and password fields
     * @return generated JWT
     * @throws AuthenticationException
     */
    @PostMapping(SIGNUP_URL)
    @ApiOperation(value = "Create new user in the databae and return authentication token to be used in future requests", response = JwtAuthenticationResponse.class)
    public JwtAuthenticationResponse createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {

        final String username = authenticationRequest.getUsername();
        final String email = authenticationRequest.getEmail();
        final String password = authenticationRequest.getPassword();
        log.debug("[POST] CREATING TOKEN FOR User " + username);

        if(this.userService.findByUsername(username) != null) {
           throw new UserAlreadyExistsException();
        }

        if(this.userService.findByEmail(email) != null) {
            throw new UserAlreadyExistsException();
        }

        Role role = roleService.findDefault();
        userService.save(new User(username, email, password, role));
        JwtUser userDetails;

        try {
            userDetails = (JwtUser) userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            log.error(ex.getMessage());
            throw new UserNotFoundException();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new UserAuthenticationException(ex);
        }

        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(userDetails);
        return new JwtAuthenticationResponse(token);
    }

    /**
     * Returns authentication token for given user
     * @param authenticationRequest with username and password
     * @return generated JWT
     * @throws AuthenticationException
     */
    @PostMapping(SIGNIN_URL)
    @ApiOperation(value = "Return authentication token to be used in future requests if the credenttial were correct", 
    			  response = JwtAuthenticationResponse.class,
    			  notes = "Email can be omited")
    public JwtAuthenticationResponse getAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {

        final String username = authenticationRequest.getUsername();
        final String password = authenticationRequest.getPassword();
        log.debug("[POST] GETTING TOKEN FOR User " + username);
        JwtUser userDetails;
        
        log.debug(passwordEncoder.encode("adidas"));

        try {
            userDetails = (JwtUser) userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException | NoResultException ex) {
            log.error(ex.getMessage());
            throw new UserNotFoundException(ex.getLocalizedMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new UserAuthenticationException(ex);
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new InvalidPasswordException();
        }

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(userDetails);
        
        log.trace(token);
        
        return new JwtAuthenticationResponse(token);
    }

    /**
     * Refreshes token
     * @param request with old JWT
     * @return Refreshed JWT
     */
	/*
	 * @PostMapping(REFRESH_TOKEN_URL) public JwtAuthenticationResponse
	 * refreshAuthenticationToken(HttpServletRequest request) { String token =
	 * request.getHeader(tokenHeader); log.debug("[POST] REFRESHING TOKEN"); String
	 * refreshedToken = jwtUtil.refreshToken(token); return new
	 * JwtAuthenticationResponse(refreshedToken); }
	 */
}
