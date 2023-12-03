package com.simplepicpay.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.simplepicpay.repository.UserRepository;

public class EmailUniqueValidation extends BaseValidation {
	@Autowired
	private UserRepository userRepository;
	private Long id;
	private String email;
	
	public EmailUniqueValidation(Long id, String email) {
		super("There is already a client with the informed e-mail");
		this.id = id;
		this.email = email;
	}

	@Override
	public boolean valid() {
		return this.userRepository.findByEmail(id, this.email).isEmpty();
	}
}
