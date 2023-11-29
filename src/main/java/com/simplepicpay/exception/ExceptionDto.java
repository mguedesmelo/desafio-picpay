package com.simplepicpay.exception;

public record ExceptionDto(int httpStatusCode, String message) {
	// Empty
}
