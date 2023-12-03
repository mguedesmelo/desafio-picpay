package com.simplepicpay.exception;

import com.simplepicpay.shared.MessageResources;

public class BusinessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5271264973005114027L;

	public BusinessException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		// TODO Testar mudando o locale
		return MessageResources.getText(super.getMessage());
	}
}
