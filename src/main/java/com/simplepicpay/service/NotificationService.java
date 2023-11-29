package com.simplepicpay.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.simplepicpay.model.User;
import com.simplepicpay.shared.Constants;

public class NotificationService extends BaseService {
	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("rawtypes")
	public void notifyUser(User user) {
		ResponseEntity<Map> response = this.restTemplate.getForEntity(
				Constants.URL_REST_NOTIFICATION, Map.class);
		System.out.println(response.getStatusCode().equals(HttpStatus.OK) && 
				response.getBody().get("message").toString().equalsIgnoreCase("true"));
	}
}
