package com.simplepicpay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.simplepicpay.model.User;

public interface UserRepository extends BaseRepository<User> {
	@Query("SELECT u FROM User u WHERE u.id <> :id AND u.document = :document")
	public Optional<User> findByDocument(@Param("id") Long id, @Param("document") String document);

	public Optional<User> findByDocument(String document);

	@Query("SELECT u FROM User u WHERE u.id <> :id AND u.email = :email")
	public Optional<User> findByEmail(@Param("id") Long id, @Param("email") String email);

	public Optional<User> findByEmail(String email);
}
