package com.simplepicpay.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseMessage {
	private int errorCode;
    private String message;

	@Override
	public String toString() {
		return String.format("{\n\t\"errorCode\":\"%s\",\n\t\"message\":\"%s\"\n}", errorCode, message);
	}
}
