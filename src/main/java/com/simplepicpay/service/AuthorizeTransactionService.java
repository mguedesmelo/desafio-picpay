package com.simplepicpay.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.simplepicpay.model.User;
import com.simplepicpay.shared.Constants;

public class AuthorizeTransactionService extends BaseService {
	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("rawtypes")
	public boolean authorize(User payer, BigDecimal ammount) {
		ResponseEntity<Map> response = this.restTemplate.getForEntity(
				Constants.URL_REST_AUTH_TRANSACTION, Map.class);
		return (response.getStatusCode().equals(HttpStatus.OK) && 
				response.getBody().get("message").toString().equalsIgnoreCase("Autorizado"));
	}
}
