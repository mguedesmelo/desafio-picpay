package com.simplepicpay.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.simplepicpay.dto.UserRequestDto;
import com.simplepicpay.repository.UserRepository;

public class DocumentUniqueValidation extends BaseValidation {
	@Autowired
	private UserRepository userRepository;
	
	private UserRequestDto userRequestDto;
	
	public DocumentUniqueValidation(UserRequestDto userRequestDto) {
		super("There is already a client with the informed document");
		this.userRequestDto = userRequestDto;
	}

	@Override
	public boolean valid() {
		return this.userRepository.findByDocument(this.userRequestDto.document()).isEmpty();
	}
}
