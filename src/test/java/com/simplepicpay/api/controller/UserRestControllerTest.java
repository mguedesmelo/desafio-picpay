package com.simplepicpay.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.simplepicpay.dto.LoginResponseDto;
import com.simplepicpay.dto.UserRequestDto;
import com.simplepicpay.model.User;
import com.simplepicpay.model.UserRole;
import com.simplepicpay.model.UserType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserRestControllerTest extends BaseRestControllerTest {
	@Test
	void testFindAll() throws Exception {
		LoginResponseDto loginResponseDto = getValidToken();
		MvcResult result = mvc.perform(MockMvcRequestBuilders
	            .get("/api/user")
	            .servletPath("/api/user")
	            .contentType(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Bearer " + loginResponseDto.token()))
	            .andExpect(status().isOk())
	            .andReturn();

		ObjectMapper mapper = new ObjectMapper();
    	mapper.registerModule(new JavaTimeModule());
    	List<User> userList = (List<User>) mapper.readValue(
    			result.getResponse().getContentAsString(), 
				new TypeReference<List<User>>() { });
		assertFalse(userList.isEmpty());
		userList.forEach(u -> {
			assertInstanceOf(User.class, u);
		});
	}

	@Test
	void testSaveCompany() throws Exception {
		UserRequestDto userCompany = new UserRequestDto(
				null, 
				"Company Outlet", 
				"outlet@picpay.com", 
				"$2a$10$SDelfowfCRWei0rkUI5IIO1dLNKYrcHP4cjbjoJLViYj4h/0a7VdO", 
				BigDecimal.valueOf(150), 
				"99999999999999", 
				UserType.COMPANY.name(), 
				UserRole.USER.name());

		MvcResult resultUserCompany = mvc.perform(MockMvcRequestBuilders
	            .post("/api/open/user")
	            .servletPath("/api/open/user")
	            .content(toJson(userCompany))
	            .contentType(MediaType.APPLICATION_JSON)
	            )
	            .andExpect(status().isOk())
	            .andReturn();
		User userCreatedCompany = (User) fromJson(
				resultUserCompany.getResponse().getContentAsString(), 
				User.class);
		assertTrue(userCreatedCompany.getId() != null);
	}

	@Test
	void testSaveCustomer() throws Exception {
		UserRequestDto userCustomer = new UserRequestDto(
				null, 
				"Beltrano Foo", 
				"beltrano@picpay.com", 
				"$2a$10$SDelfowfCRWei0rkUI5IIO1dLNKYrcHP4cjbjoJLViYj4h/0a7VdO", 
				new BigDecimal(50),	
				"66666666666", 
				UserType.CUSTOMER.name(), 
				UserRole.USER.name());

		MvcResult resultUserCustomer = mvc.perform(MockMvcRequestBuilders
	            .post("/api/open/user")
	            .servletPath("/api/open/user")
	            .content(toJson(userCustomer))
	            .contentType(MediaType.APPLICATION_JSON)
	            )
	            .andExpect(status().isOk())
	            .andReturn();
		User userCreatedCustomer = (User) fromJson(
				resultUserCustomer.getResponse().getContentAsString(), 
				User.class);
		assertTrue(userCreatedCustomer.getId() != null);
	}

	@Test
	void testSaveEmailAlreadyExists() throws Exception {
		UserRequestDto userCustomer = new UserRequestDto(
				null, 
				"Ciclano Foo", 
				"ciclano@picpay.com", 
				"$2a$10$SDelfowfCRWei0rkUI5IIO1dLNKYrcHP4cjbjoJLViYj4h/0a7VdO", 
				new BigDecimal(50),	
				"66666666666", 
				UserType.CUSTOMER.name(), 
				UserRole.USER.name());

		MvcResult resultUserCustomer = mvc.perform(MockMvcRequestBuilders
	            .post("/api/open/user")
	            .servletPath("/api/open/user")
	            .content(toJson(userCustomer))
	            .contentType(MediaType.APPLICATION_JSON)
	            )
	            .andExpect(status().isBadRequest())
	            .andReturn();
		assertEquals(resultUserCustomer.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testSaveDocumentAlreadyExists() throws Exception {
		UserRequestDto userCustomer = new UserRequestDto(
				null, 
				"Ciclano Foo", 
				"newcustomer@picpay.com", 
				"$2a$10$SDelfowfCRWei0rkUI5IIO1dLNKYrcHP4cjbjoJLViYj4h/0a7VdO", 
				new BigDecimal(50),	
				"86126937000123", 
				UserType.COMPANY.name(), 
				UserRole.USER.name());

		MvcResult resultUserCustomer = mvc.perform(MockMvcRequestBuilders
	            .post("/api/open/user")
	            .servletPath("/api/open/user")
	            .content(toJson(userCustomer))
	            .contentType(MediaType.APPLICATION_JSON)
	            )
	            .andExpect(status().isBadRequest())
	            .andReturn();
		assertEquals(resultUserCustomer.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());

//		assertThatThrownBy(
//            () -> mvc.perform(MockMvcRequestBuilders
//    	            .post("/api/user")
//    	            .content(toJson(userCustomer))
//    	            .contentType(MediaType.APPLICATION_JSON)
//    	            .header("Authorization", "Bearer " + loginResponseDto.token())))
//		.hasCauseInstanceOf(RuntimeException.class)
//		.hasMessageContaining("The exception message");
	}

	@Test
	void testSaveInvalidFields() throws Exception {
		fail("UserRestControllerTest.testSaveInvalidFields not implemented");
	}

	@Test
	void testSaveMissingFields() throws Exception {
		fail("UserRestControllerTest.testSaveMissingFields not implemented");
	}
}
