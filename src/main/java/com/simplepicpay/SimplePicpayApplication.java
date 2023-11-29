package com.simplepicpay;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.simplepicpay.model.User;
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
		this.userService.save(new User("Amazon", "Shopping", "amazon@example.com", "123", 
				new BigDecimal(20),	"18469813000190", UserType.MERCHANT, UserRole.USER));
		this.userService.save(new User("Fulano", "Foo", "fulano@example.com", "123", 
				new BigDecimal(50),	"11111111111", UserType.CUSTOMER, UserRole.USER));
		this.userService.save(new User("Ciclano", "Bar", "ciclano@example.com", "123", 
				new BigDecimal(50),	"22222222222", UserType.CUSTOMER, UserRole.USER));

		this.userService.findAll().forEach(u -> System.out.println(u));
	}
}
