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
		return String.format("{\"errorCode\":%s,\"message\":\"%s\"}", errorCode, message);
	}
}
