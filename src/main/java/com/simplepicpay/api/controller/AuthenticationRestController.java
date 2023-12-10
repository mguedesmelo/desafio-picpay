package com.simplepicpay.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplepicpay.dto.LoginRequestDto;
import com.simplepicpay.dto.LoginResponseDto;
import com.simplepicpay.service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthenticationRestController {
	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/open/signin")
	public ResponseEntity<LoginResponseDto> signin(@RequestBody @Valid LoginRequestDto loginRequestDto) {
		String token = this.authenticationService.authenticate(loginRequestDto.email(), 
				loginRequestDto.password());

		return ResponseEntity.ok(new LoginResponseDto(token, loginRequestDto.email()));
	}
}
