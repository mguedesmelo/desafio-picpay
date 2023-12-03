package com.simplepicpay.validation;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.simplepicpay.shared.Constants;

public class AuthorizeTransferValidation extends BaseValidation {
	private RestTemplate restTemplate;

	public AuthorizeTransferValidation() {
		super("Transfer not authorized");
		this.restTemplate = new RestTemplate();
	}

	@Override
	public boolean valid() {
		return this.authorize();
	}
	
	@SuppressWarnings("rawtypes")
	public boolean authorize() {
		this.restTemplate = new RestTemplate();
		ResponseEntity<Map> response = this.restTemplate.getForEntity(
				Constants.URL_REST_AUTH_TRANSFER, Map.class);
		return (response.getStatusCode().equals(HttpStatus.OK) && 
				response.getBody().get("message").toString().equalsIgnoreCase("Autorizado"));
	}
}
