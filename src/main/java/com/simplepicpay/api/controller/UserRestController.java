package com.simplepicpay.api.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplepicpay.dto.LoginRequestDto;
import com.simplepicpay.dto.LoginResponseDto;
import com.simplepicpay.dto.UserRequestDto;
import com.simplepicpay.exception.BusinessException;
import com.simplepicpay.model.User;
import com.simplepicpay.service.TokenService;
import com.simplepicpay.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<User> save(@RequestBody UserRequestDto userDto) throws BusinessException {
		User user = this.userService.save(userDto);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> toReturn = this.userService.findAll();
		return new ResponseEntity<List<User>>(toReturn, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto authenticationDto) {
		UsernamePasswordAuthenticationToken userNamePassword = new UsernamePasswordAuthenticationToken(
				authenticationDto.email(), authenticationDto.password());
		Authentication auth = this.authenticationManager.authenticate(userNamePassword);
		String token = this.tokenService.generateToken((User) auth.getPrincipal());

		return ResponseEntity.ok(new LoginResponseDto(token));
	}
}
