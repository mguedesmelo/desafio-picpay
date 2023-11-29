package com.simplepicpay.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.simplepicpay.service.AuthorizeTransferService;

public class AuthorizeTransferValidation extends BaseValidation {
	@Autowired
	private AuthorizeTransferService authorizeTransferService;

	public AuthorizeTransferValidation() {
		super("Transfer not authorized");
	}

	@Override
	public boolean valid() {
		return this.authorizeTransferService.authorize();
	}
}
