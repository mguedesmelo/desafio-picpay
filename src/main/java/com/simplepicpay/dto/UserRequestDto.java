package com.simplepicpay.dto;

import java.math.BigDecimal;

import com.simplepicpay.annotation.ValueOfEnum;
import com.simplepicpay.model.UserRole;
import com.simplepicpay.model.UserType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDto(
		Long id,

		@NotBlank 
		@NotNull
		String name,

		@NotBlank 
		@NotNull
		String email,

		@NotBlank 
		@NotNull
		String password,

		@NotNull
		BigDecimal balance,

		@NotBlank 
		@NotNull
		String document,

		@NotBlank 
		@NotNull
		@ValueOfEnum(enumClass = UserType.class) 
		String userType,
	    
		@NotBlank 
		@NotNull
		@ValueOfEnum(enumClass = UserRole.class)
		String userRole
		) {

}
