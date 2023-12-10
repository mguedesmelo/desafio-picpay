package com.simplepicpay.exception;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ExceptionDto> threatThrowable(Throwable throwable) {
		return ResponseEntity.badRequest().body(new ExceptionDto(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error"));
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<ExceptionDto> threatInvalidDataAccessApiUsageException(
			InvalidDataAccessApiUsageException e) {
		return ResponseEntity.badRequest().body(new ExceptionDto(HttpStatus.BAD_REQUEST.value(), 
				"Missing fields"));
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ExceptionDto> threatNullPointerException(NullPointerException e) {
		return ResponseEntity.badRequest().body(new ExceptionDto(HttpStatus.BAD_REQUEST.value(), 
				"Missing fields"));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ExceptionDto> threatHttpMessageNotReadableException(
			HttpMessageNotReadableException e) {
		return ResponseEntity.badRequest().body(new ExceptionDto(HttpStatus.BAD_REQUEST.value(), 
				"Invalid fields"));
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ExceptionDto> threatBusinessException(BusinessException businessException) {
		return ResponseEntity.badRequest().body(new ExceptionDto(
				HttpStatus.BAD_REQUEST.value(), businessException.getMessage()));
	}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException e) {
    	return new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Invalid email or password").toString();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUsernameNotFoundException(UsernameNotFoundException e) {
    	return new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Invalid email or password").toString();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public String handleValidationException(ValidationException e) {
        return new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Invalid fields").toString();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    	return new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Invalid fields").toString();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationException(ConstraintViolationException e) {
    	return new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Invalid fields").toString();
    }
}
