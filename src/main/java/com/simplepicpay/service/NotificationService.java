package com.simplepicpay.service;

import org.springframework.stereotype.Service;

import com.simplepicpay.model.User;

@Service
public class NotificationService {
//	@Autowired
//	private RestTemplate restTemplate;
//
//	@Bean
//	RestTemplate restTemplate(RestTemplateBuilder builder){
//	    return builder.build();
//	}

//	@SuppressWarnings("rawtypes")
	public void notifyUser(User user) {
//		ResponseEntity<Map> response = this.restTemplate.getForEntity(
//				Constants.URL_REST_NOTIFICATION, Map.class);
//		System.out.println(response.getStatusCode().equals(HttpStatus.OK) && 
//				response.getBody().get("message").toString().equalsIgnoreCase("true"));
	}
}
