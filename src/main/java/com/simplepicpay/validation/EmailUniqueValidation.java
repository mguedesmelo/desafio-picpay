package com.simplepicpay.validation;

import com.simplepicpay.repository.UserRepository;

public class EmailUniqueValidation extends BaseValidation {
	private UserRepository userRepository;
	private Long id;
	private String email;
	
	public EmailUniqueValidation(Long id, String email, UserRepository userRepository) {
		super("There is already a client with the informed e-mail");
		this.id = id;
		this.email = email;
		this.userRepository = userRepository;
	}

	@Override
	public boolean valid() {
		return this.userRepository.findByEmail(id, this.email).isEmpty();
	}
}
