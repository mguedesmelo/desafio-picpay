package com.simplepicpay.validation;

import java.math.BigDecimal;

import com.simplepicpay.model.User;

public class PayerHasBalanceValidation extends BaseValidation {
	private User payer;
	private BigDecimal ammount;

	public PayerHasBalanceValidation(User payer, BigDecimal ammount) {
		this.payer = payer;
	}

	@Override
	boolean valid() {
		return this.payer.getBalance().compareTo(this.ammount) >= 1;
	}
}
