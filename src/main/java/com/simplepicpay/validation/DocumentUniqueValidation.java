package com.simplepicpay.validation;

import com.simplepicpay.repository.UserRepository;

public class DocumentUniqueValidation extends BaseValidation {
	private UserRepository userRepository;
	private Long id;
	private String document;

	public DocumentUniqueValidation(Long id, String document, UserRepository userRepository) {
		super("There is already a client with the informed document");
		this.id = id;
		this.document = document;
		this.userRepository = userRepository;
	}

	@Override
	public boolean valid() {
		return this.userRepository.findByDocument(this.id, this.document).isEmpty();
	}
}
