package com.simplepicpay.validation;

public abstract class BaseValidation {
	private String message;

	public BaseValidation(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public abstract boolean valid();
}
