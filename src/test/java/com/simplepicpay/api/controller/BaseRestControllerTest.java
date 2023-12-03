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
	
    protected Object fromJson(String json, Class<? extends Object> clazz) {
        try {
        	ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
