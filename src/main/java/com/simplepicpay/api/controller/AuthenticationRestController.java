package com.simplepicpay.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplepicpay.dto.LoginRequestDto;
import com.simplepicpay.dto.LoginResponseDto;
import com.simplepicpay.exception.BusinessException;
import com.simplepicpay.model.User;
import com.simplepicpay.service.TokenService;
import com.simplepicpay.service.UserService;
import com.simplepicpay.shared.StringUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthenticationRestController {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenService tokenService;

	@GetMapping("/me")
	public User me() throws BusinessException {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!StringUtil.isNullOrEmpty(email)) {
            return this.userService.findByEmail(email);
        }
        return null;
	}

	@PostMapping("/open/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginRequestDto.email(), loginRequestDto.password());
		Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
		if (authentication.getPrincipal() == null) {
			throw new BusinessException("Invalid email or password");
		}
		String token = this.tokenService.generateToken((User) authentication.getPrincipal());

		return ResponseEntity.ok(new LoginResponseDto(token, loginRequestDto.email()));
	}
}
