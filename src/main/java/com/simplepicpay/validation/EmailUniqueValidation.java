package com.simplepicpay.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.simplepicpay.dto.UserRequestDto;
import com.simplepicpay.repository.UserRepository;

public class EmailUniqueValidation extends BaseValidation {
	@Autowired
	private UserRepository userRepository;
	
	private UserRequestDto userRequestDto;
	
	public EmailUniqueValidation(UserRequestDto userRequestDto) {
		super("There is already a client with the informed e-mail");
		this.userRequestDto = userRequestDto;
	}

	@Override
	public boolean valid() {
		return this.userRepository.findByDocument(this.userRequestDto.email()).isEmpty();
	}
}
