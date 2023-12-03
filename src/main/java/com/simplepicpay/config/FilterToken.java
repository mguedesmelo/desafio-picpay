package com.simplepicpay.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.simplepicpay.model.User;
import com.simplepicpay.service.TokenService;
import com.simplepicpay.service.UserService;
import com.simplepicpay.shared.HttpUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterToken extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpUtil httpUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
    		HttpServletResponse response, 
    		FilterChain filterChain) throws ServletException, IOException {
        var authorizationHeader = request.getHeader("Authorization");
        if (httpUtil.isFreeToNavigate(request.getServletPath())) {
        	filterChain.doFilter(request, response);
        } else if (authorizationHeader == null) {
        	httpUtil.sendResponseMessage(response, HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        } else {
        	try {
        		String token = authorizationHeader.replace("Bearer ", "");

        		String subject = this.tokenService.getSubject(token);

	            User user = this.userService.findByEmail(subject);
	
	            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
	            		user, null, user.getAuthorities());

	            SecurityContextHolder.getContext().setAuthentication(authentication);

	            filterChain.doFilter(request, response);
        	} catch (TokenExpiredException e) {
        		httpUtil.sendResponseMessage(response, HttpStatus.UNAUTHORIZED.value(), 
        				"Unauthorized - invalid session");
        	}
        }
    }
}
