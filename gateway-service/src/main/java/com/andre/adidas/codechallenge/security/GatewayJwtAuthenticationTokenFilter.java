package com.andre.adidas.codechallenge.security;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.andre.adidas.codechallenge.jwt.JwtUser;
import com.andre.adidas.codechallenge.jwt.JwtUtil;
import com.netflix.zuul.context.RequestContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.extern.slf4j.Slf4j;

/**
 * @author André da Silva Medeiros
 * Interceptor to forward the token to the application under the gateway umbrella
 */
@Slf4j
public class GatewayJwtAuthenticationTokenFilter extends OncePerRequestFilter {
	private JwtUtil jwtTokenUtil;

	@Value("${auth.header}")
	private String tokenHeader;
	
	@Value("${auth.prefix}")
	private String tokenPrefix;
	
	@Autowired
	public void setJwtTokenUtil(JwtUtil jwtUtil) {
		this.jwtTokenUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		
		String authToken = request.getHeader(this.tokenHeader);
		if (authToken != null) {
			
			SignedJWT signedJWT = jwtTokenUtil.validate(authToken);
			
			try {
				setInContext(request, signedJWT);
			} catch (ParseException e) {
				log.error(e.getMessage());
				throw new ServletException(e);
			}
			
			RequestContext.getCurrentContext().addZuulRequestHeader(tokenHeader, authToken);
		}

		chain.doFilter(request, response);
	}

	private void setInContext(HttpServletRequest request, SignedJWT signedJWT) throws ParseException {
		JWTClaimsSet claimsSet = jwtTokenUtil.getClaimsFromToken(signedJWT);
		@SuppressWarnings("unchecked")
		Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) claimsSet.getClaim(JwtUtil.CLAIM_KEY_AUTHORITIES);
		UserDetails userDetails = (UserDetails) new JwtUser(claimsSet.getLongClaim(JwtUtil.CLAIM_KEY_ID), claimsSet.getSubject(), null, null, authorities);
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		logger.debug("Authenticated user " + userDetails.getUsername() + ", setting security context");
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}

