package com.simplepicpay.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplepicpay.dto.TransferRequestDto;
import com.simplepicpay.exception.BusinessException;
import com.simplepicpay.model.Transaction;
import com.simplepicpay.service.UserService;

@RestController
@RequestMapping("/api/transfer")
public class TransactionRestController {
	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<Transaction> transfer(
			@RequestBody TransferRequestDto transferDto) throws BusinessException {
		Transaction transaction = this.userService.transfer(
				transferDto.payer(), 
				transferDto.payee(), 
				transferDto.value());
		return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
	}
}
