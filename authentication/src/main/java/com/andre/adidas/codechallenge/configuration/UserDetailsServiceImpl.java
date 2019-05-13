package com.andre.adidas.codechallenge.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.andre.adidas.codechallenge.auth.JwtUser;
import com.andre.adidas.codechallenge.entities.User;
import com.andre.adidas.codechallenge.services.UserService;

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found.");
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		
		if(user.getRole() != null) {
			grantedAuthorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole().getLabel()));
		}
		
		return new JwtUser(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), grantedAuthorities);
	}

}
