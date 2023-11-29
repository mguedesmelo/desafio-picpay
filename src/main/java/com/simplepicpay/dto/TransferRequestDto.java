package com.simplepicpay.dto;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record TransferRequestDto(
		@NotNull
		Long payer, 
		
		@NotNull
		Long payee, 
		
		@NotNull
		BigDecimal value
		) {
	// Empty
}
