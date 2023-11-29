package com.simplepicpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ExceptionDto> threatBusinessException(BusinessException businessException) {
		return ResponseEntity.badRequest().body(new ExceptionDto(
				HttpStatus.BAD_REQUEST.value(), businessException.getMessage()));
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ExceptionDto> threatThrowable(Throwable throwable) {
		return ResponseEntity.badRequest().body(new ExceptionDto(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro inesperado"));
	}
}
