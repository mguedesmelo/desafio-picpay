package com.simplepicpay.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.simplepicpay.dto.LoginResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthenticationRestControllerTest extends BaseRestControllerTest {
	@Test
	void testLogin() throws Exception {
		LoginResponseDto loginResponseDto = getValidToken();

		assertNotNull(loginResponseDto);
		assertNotNull(loginResponseDto.token());
		assertEquals(loginResponseDto.email(), "fulano@picpay.com");
	}

	@Test
	void testLoginInvalidEmail() throws Exception {
		MvcResult result = performLogin("invalid@picpay.com", "h3ll0", status().isBadRequest());

		assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testLoginInvalidPassword() throws Exception {
		MvcResult result = performLogin("fulano@picpay.com", "invalid", status().isBadRequest());

		assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
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
		// TODO Endpoint /api/me deve retornar um objeto DTO??
//		User userJson = (User) fromJson(result.getResponse().getContentAsString(), User.class);
		String userJson = result.getResponse().getContentAsString();
		assertNotNull(userJson);
	}
}
