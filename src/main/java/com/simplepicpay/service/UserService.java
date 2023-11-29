package com.simplepicpay.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplepicpay.model.Transaction;
import com.simplepicpay.model.User;
import com.simplepicpay.repository.TransactionRepository;
import com.simplepicpay.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService extends BaseService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	public User save(User user) {
		return this.userRepository.save(user);
	}

	public void delete(User user) {
		this.userRepository.delete(user);
	}
	
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Transactional
	public void transfer(User payer, User payee, BigDecimal ammount) {
		Transaction transaction = new Transaction(payer, payee, ammount);
		payer.setBalance(payer.getBalance().subtract(ammount));
		payee.setBalance(payee.getBalance().add(ammount));
		this.transactionRepository.save(transaction);
		this.userRepository.saveAll(List.of(payee, payer));
	}
}
