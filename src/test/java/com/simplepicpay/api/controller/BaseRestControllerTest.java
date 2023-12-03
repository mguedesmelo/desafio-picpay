package com.simplepicpay.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseRestControllerTest<T> {
	protected String toJson(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
