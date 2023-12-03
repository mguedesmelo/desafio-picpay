package com.simplepicpay.exception;

public class BusinessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5271264973005114027L;

	public BusinessException(String message) {
		super(message);
	}
}
