package com.simplepicpay.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.simplepicpay.model.User;
import com.simplepicpay.shared.Constants;

@Service
public class TokenService extends BaseService {
	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(Constants.JWT_SECRET_KEY);
			String token = JWT.create()
					.withIssuer(Constants.JWT_ISSUER)
					.withSubject(user.getEmail())
					.withExpiresAt(generateExpirationDate())
					.sign(algorithm);
			return token;
		} catch (Exception e) {
			throw new RuntimeException("Error while generating token", e);
		}
	}

	public String validateToken(String token) {
		try {
			return JWT.require(Algorithm.HMAC256(Constants.JWT_SECRET_KEY))
					.withIssuer(Constants.JWT_ISSUER)
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			return "";
		}
	}

	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
	public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(Constants.JWT_SECRET_KEY))
                .withIssuer(Constants.JWT_ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }
}
