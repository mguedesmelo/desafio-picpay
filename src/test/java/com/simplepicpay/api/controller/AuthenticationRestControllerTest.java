package com.simplepicpay.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.simplepicpay.dto.LoginRequestDto;
import com.simplepicpay.dto.LoginResponseDto;
import com.simplepicpay.model.User;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthenticationRestControllerTest extends BaseRestControllerTest<User> {
	@Autowired
    private MockMvc mvc;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// Empty
	}

	@BeforeEach
	void setUp() throws Exception {
		// Empty
	}

	private String getToken(String email, String password) throws Exception {
		MvcResult result = perform(email, password, status().isOk());
		
		return result.getResponse().getContentAsString();
	}

	private LoginResponseDto getValidToken() throws Exception {
		String token = this.getToken("fulano@picpay.com", "h3ll0");
		return (LoginResponseDto) fromJson(token, LoginResponseDto.class);
	}

	private MvcResult perform(String email, String password, ResultMatcher expectedStatus) throws Exception {
		return mvc.perform(MockMvcRequestBuilders
				.post("/api/login")
				.servletPath("/api/login")
				.content(toJson(new LoginRequestDto(email, password)))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(expectedStatus)
				.andReturn();
	}
	
	@Test
	void testLogin() throws Exception {
		LoginResponseDto loginResponseDto = getValidToken();
		assertNotNull(loginResponseDto);
	}

	@Test
	void testLoginInvalidEmail() throws Exception {
		MvcResult result = perform("invalid@picpay.com", "h3ll0", status().isBadRequest());

		int statusCode = result.getResponse().getStatus();

		assertEquals(statusCode, 400);
	}

	@Test
	void testLoginInvalidPassword() throws Exception {
		MvcResult result = perform("fulano@picpay.com", "invalid", status().isBadRequest());

		int statusCode = result.getResponse().getStatus();

		assertEquals(statusCode, 400);
	}

	@Test
	void testMe() throws Exception {
		LoginResponseDto loginResponseDto = getValidToken();
		MvcResult result = mvc.perform(MockMvcRequestBuilders
	            .get("/api/me")
	            .contentType(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Bearer " + loginResponseDto.token()))
	            .andExpect(status().isOk())
	            .andReturn();
		// FIXME Endpoint /api/me deve retornar um objeto DTO??
//		User userJson = (User) fromJson(result.getResponse().getContentAsString(), User.class);
		String userJson = result.getResponse().getContentAsString();
		assertNotNull(userJson);
	}
}
