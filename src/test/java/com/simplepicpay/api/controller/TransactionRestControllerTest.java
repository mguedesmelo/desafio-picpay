package com.simplepicpay.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.simplepicpay.dto.LoginResponseDto;
import com.simplepicpay.dto.TransferRequestDto;
import com.simplepicpay.exception.ExceptionDto;
import com.simplepicpay.model.Transaction;
import com.simplepicpay.model.User;
import com.simplepicpay.service.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TransactionRestControllerTest extends BaseRestControllerTest {
	private Long companyId = 1l;
	private Long customerId = 2l;
	private LoginResponseDto loginResponseDto;
	@Autowired
	private UserService userService;

	@BeforeEach
	void setUp() throws Exception {
		this.loginResponseDto = getValidToken();
	}
	
	private User findById(Long id) {
		return this.userService.findById(id);
	}

	@Test
	void testTransfer() throws Exception {
		Long payer = this.customerId;
		Long payee = this.companyId;
		BigDecimal ammount = BigDecimal.valueOf(5);
		BigDecimal payerBalanceBefore = this.findById(payer).getBalance();
		BigDecimal payeeBalanceBefore = this.findById(payee).getBalance();

		TransferRequestDto transferDto = new TransferRequestDto(payer, payee, ammount);
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.post("/api/transfer")
				.servletPath("/api/transfer")
				.content(toJson(transferDto))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + this.loginResponseDto.token()))
				.andExpect(status().isOk())
				.andReturn();

		Transaction transaction = (Transaction) fromJson(
				result.getResponse().getContentAsString(), 
				Transaction.class);
		assertTrue(transaction.getId() != null && transaction.getId() > 0);
		assertEquals(transaction.getPayer().getBalance(), payerBalanceBefore.subtract(ammount), "Payer balance has not changed");
		assertEquals(transaction.getPayee().getBalance(), payeeBalanceBefore.add(ammount), "Payee balance has not changed");
	}

	@Test
	void testTransferPayerNotFound() throws Exception {
		Long payer = 3000l;
		Long payee = this.companyId;
		BigDecimal ammount = BigDecimal.valueOf(5);
		BigDecimal payeeBalanceBefore = this.findById(payee).getBalance();

		TransferRequestDto transferDto = new TransferRequestDto(payer, payee, ammount);
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.post("/api/transfer")
				.servletPath("/api/transfer")
				.content(toJson(transferDto))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + this.loginResponseDto.token()))
				.andExpect(status().isBadRequest())
				.andReturn();

		ExceptionDto exceptionDto = (ExceptionDto) fromJson(
				result.getResponse().getContentAsString(), ExceptionDto.class);
		assertEquals(exceptionDto.httpStatusCode(), HttpStatus.BAD_REQUEST.value());
		System.out.println(exceptionDto == null ? "" : exceptionDto.message());

		BigDecimal payeeBalance = this.findById(payee).getBalance();
		assertEquals(payeeBalanceBefore, payeeBalance, "Payee balance cannot change");
	}

	@Test
	void testTransferPayeeNotFound() throws Exception {
		Long payer = this.customerId;
		Long payee = 3000l;
		BigDecimal ammount = BigDecimal.valueOf(5);
		BigDecimal payerBalanceBefore = this.findById(payer).getBalance();

		TransferRequestDto transferDto = new TransferRequestDto(payer, payee, ammount);
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.post("/api/transfer")
				.servletPath("/api/transfer")
				.content(toJson(transferDto))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + this.loginResponseDto.token()))
				.andExpect(status().isBadRequest())
				.andReturn();

		ExceptionDto exceptionDto = (ExceptionDto) fromJson(
				result.getResponse().getContentAsString(), ExceptionDto.class);
		assertEquals(exceptionDto.httpStatusCode(), HttpStatus.BAD_REQUEST.value());
		System.out.println(exceptionDto == null ? "" : exceptionDto.message());

		BigDecimal payerBalance = this.findById(payer).getBalance();
		assertEquals(payerBalanceBefore, payerBalance, "Payer balance cannot change");
	}

	@Test
	void testTransferPayerOnlyCustomerCanTransfer() throws Exception {
		Long payer = this.companyId;
		Long payee = this.customerId;
		BigDecimal ammount = BigDecimal.valueOf(5);
		BigDecimal payerBalanceBefore = this.findById(payer).getBalance();
		BigDecimal payeeBalanceBefore = this.findById(payee).getBalance();

		TransferRequestDto transferDto = new TransferRequestDto(payer, payee, ammount);
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.post("/api/transfer")
				.servletPath("/api/transfer")
				.content(toJson(transferDto))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + this.loginResponseDto.token()))
				.andExpect(status().isBadRequest())
				.andReturn();

		ExceptionDto exceptionDto = (ExceptionDto) fromJson(
				result.getResponse().getContentAsString(), ExceptionDto.class);
		assertEquals(exceptionDto.httpStatusCode(), HttpStatus.BAD_REQUEST.value());
		System.out.println(exceptionDto == null ? "" : exceptionDto.message());

		BigDecimal payerBalance = this.findById(payer).getBalance();
		BigDecimal payeeBalance = this.findById(payee).getBalance();
		assertEquals(payerBalanceBefore, payerBalance, "Payer balance cannot change");
		assertEquals(payeeBalanceBefore, payeeBalance, "Payee balance cannot change");
	}

	@Test
	void testTransferPayerHasBalance() throws Exception {
		Long payer = this.customerId;
		Long payee = this.companyId;
		BigDecimal ammount = BigDecimal.valueOf(50000);
		BigDecimal payerBalanceBefore = this.findById(payer).getBalance();
		BigDecimal payeeBalanceBefore = this.findById(payee).getBalance();

		TransferRequestDto transferDto = new TransferRequestDto(payer, payee, ammount);
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.post("/api/transfer")
				.servletPath("/api/transfer")
				.content(toJson(transferDto))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + this.loginResponseDto.token()))
				.andExpect(status().isBadRequest())
				.andReturn();

		ExceptionDto exceptionDto = (ExceptionDto) fromJson(
				result.getResponse().getContentAsString(), ExceptionDto.class);
		assertEquals(exceptionDto.httpStatusCode(), HttpStatus.BAD_REQUEST.value());
		System.out.println(exceptionDto == null ? "" : exceptionDto.message());

		BigDecimal payerBalance = this.findById(payer).getBalance();
		BigDecimal payeeBalance = this.findById(payee).getBalance();
		assertEquals(payerBalanceBefore, payerBalance, "Payer balance cannot change");
		assertEquals(payeeBalanceBefore, payeeBalance, "Payee balance cannot change");
	}

	@Test
	@Disabled("This is not necessary since the external authorization service always returns true")
	void testTransferAuthorized() throws Exception {
		Long payer = this.customerId;
		Long payee = this.companyId;
		BigDecimal ammount = BigDecimal.valueOf(5);
		BigDecimal payerBalanceBefore = this.findById(payer).getBalance();
		BigDecimal payeeBalanceBefore = this.findById(payee).getBalance();

		TransferRequestDto transferDto = new TransferRequestDto(payer, payee, ammount);
		mvc.perform(MockMvcRequestBuilders
				.post("/api/transfer")
				.servletPath("/api/transfer")
				.content(toJson(transferDto))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + this.loginResponseDto.token()))
				.andExpect(status().isOk())
				.andReturn();

		BigDecimal payerBalance = this.findById(payer).getBalance();
		BigDecimal payeeBalance = this.findById(payee).getBalance();
		assertEquals(payerBalanceBefore, payerBalance, "Payer balance cannot change");
		assertEquals(payeeBalanceBefore, payeeBalance, "Payee balance cannot change");
	}
}
