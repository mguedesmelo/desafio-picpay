package com.simplepicpay.repository;

import org.springframework.security.core.userdetails.UserDetails;

import com.simplepicpay.model.User;

public interface UserRepository extends BaseRepository<User> {
	public User findByDocument(String document);
	
	public UserDetails findByEmail(String email);
}
