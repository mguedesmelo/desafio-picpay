package com.simplepicpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.simplepicpay.exception.BusinessException;
import com.simplepicpay.model.User;

@Service
public class AuthenticationService extends BaseService {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenService tokenService;

	public String authenticate(String email, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				email, password);
		Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
		if (authentication.getPrincipal() == null) {
			throw new BusinessException("Invalid email or password");
		}
		return this.tokenService.generateToken((User) authentication.getPrincipal());
	}
}
