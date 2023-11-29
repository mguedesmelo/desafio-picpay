package com.simplepicpay.validation;

import com.simplepicpay.model.User;
import com.simplepicpay.model.UserType;

public class OnlyUserCanTransferValidation extends BaseValidation {
	private User payer;

	public OnlyUserCanTransferValidation(User payer) {
		this.payer = payer;
	}

	@Override
	boolean valid() {
		return this.payer.getUserType().equals(UserType.CUSTOMER);
	}
}
