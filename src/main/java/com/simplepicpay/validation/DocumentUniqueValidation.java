package com.simplepicpay.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.simplepicpay.repository.UserRepository;

public class DocumentUniqueValidation extends BaseValidation {
	@Autowired
	private UserRepository userRepository;
	private Long id;
	private String document;

	public DocumentUniqueValidation(Long id, String document) {
		super("There is already a client with the informed document");
		this.id = id;
		this.document = document;
	}

	@Override
	public boolean valid() {
		return this.userRepository.findByDocument(this.id, this.document).isEmpty();
	}
}
