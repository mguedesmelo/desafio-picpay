package com.simplepicpay.validation;

import com.simplepicpay.model.User;
import com.simplepicpay.model.UserType;

public class OnlyUserCanTransferValidation extends BaseValidation {
	private User payer;

	public OnlyUserCanTransferValidation(User payer) {
		super("Only customers can perform transfers");
		this.payer = payer;
	}

	@Override
	public boolean valid() {
		return this.payer.getUserType().equals(UserType.CUSTOMER);
	}
}
