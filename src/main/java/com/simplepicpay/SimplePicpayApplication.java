package com.simplepicpay;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.simplepicpay.dto.UserRequestDto;
import com.simplepicpay.model.UserRole;
import com.simplepicpay.model.UserType;
import com.simplepicpay.service.UserService;

@SpringBootApplication
public class SimplePicpayApplication implements CommandLineRunner {
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SimplePicpayApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.userService.save(new UserRequestDto(
				null, 
				"Company Shopping", 
				"company@picpay.com", 
				"$2a$10$SDelfowfCRWei0rkUI5IIO1dLNKYrcHP4cjbjoJLViYj4h/0a7VdO", 
				BigDecimal.valueOf(150), 
				"86126937000123", 
				UserType.COMPANY.name(), 
				UserRole.USER.name()));
		this.userService.save(new UserRequestDto(
				null, 
				"Fulano Foo", 
				"fulano@picpay.com", 
				"$2a$10$SDelfowfCRWei0rkUI5IIO1dLNKYrcHP4cjbjoJLViYj4h/0a7VdO", 
				new BigDecimal(50),	
				"65574052687", 
				UserType.CUSTOMER.name(), 
				UserRole.USER.name()));
		this.userService.save(new UserRequestDto(
				null, 
				"Ciclano Bar", 
				"ciclano@picpay.com", 
				"$2a$10$SDelfowfCRWei0rkUI5IIO1dLNKYrcHP4cjbjoJLViYj4h/0a7VdO", 
				new BigDecimal(50),	
				"05152342570", 
				UserType.CUSTOMER.name(), 
				UserRole.USER.name()));

		this.userService.findAll().forEach(u -> System.out.println(u));
	}
}
