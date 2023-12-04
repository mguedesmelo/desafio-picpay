package com.simplepicpay.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.simplepicpay.dto.LoginRequestDto;
import com.simplepicpay.dto.LoginResponseDto;

public class BaseRestControllerTest {
	@Autowired
    protected MockMvc mvc;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// Empty
	}

	@BeforeEach
	void setUp() throws Exception {
		// Empty
	}

	protected String toJson(final Object obj) {
		try {
        	ObjectMapper mapper = new ObjectMapper();
        	mapper.registerModule(new JavaTimeModule());
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    protected Object fromJson(String json, Class<? extends Object> clazz) {
        try {
        	ObjectMapper mapper = new ObjectMapper();
        	mapper.registerModule(new JavaTimeModule());
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

    protected String getToken(String email, String password) throws Exception {
		MvcResult result = perform(email, password, status().isOk());
		
		return result.getResponse().getContentAsString();
	}

    protected LoginResponseDto getValidToken() throws Exception {
		String token = this.getToken("fulano@picpay.com", "h3ll0");
		return (LoginResponseDto) fromJson(token, LoginResponseDto.class);
	}

    protected MvcResult perform(String email, String password, ResultMatcher expectedStatus) throws Exception {
		return mvc.perform(MockMvcRequestBuilders
				.post("/api/login")
				.servletPath("/api/login")
				.content(toJson(new LoginRequestDto(email, password)))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(expectedStatus)
				.andReturn();
	}


}
