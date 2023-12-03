package com.simplepicpay.model;

public enum UserType {
	CUSTOMER("Customer"),
	COMPANY("Company");

	private String type;

	UserType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
