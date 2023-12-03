package com.simplepicpay.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.simplepicpay.dto.UserRequestDto;
import com.simplepicpay.exception.BusinessException;
import com.simplepicpay.mapper.UserMapper;
import com.simplepicpay.model.Transaction;
import com.simplepicpay.model.User;
import com.simplepicpay.repository.TransactionRepository;
import com.simplepicpay.repository.UserRepository;
import com.simplepicpay.validation.AuthorizeTransferValidation;
import com.simplepicpay.validation.DocumentUniqueValidation;
import com.simplepicpay.validation.EmailUniqueValidation;
import com.simplepicpay.validation.OnlyUserCanTransferValidation;
import com.simplepicpay.validation.PayerHasBalanceValidation;

import jakarta.transaction.Transactional;

@Service
public class UserService extends BaseService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private UserMapper userMapper;

	public User save(UserRequestDto userRequestDto) throws BusinessException {
		this.clearValidations();
		this.addValidation(new DocumentUniqueValidation(userRequestDto.id(), userRequestDto.document()));
		this.addValidation(new EmailUniqueValidation(userRequestDto.id(), userRequestDto.email()));
//		performValidations();

		return this.userRepository.save(this.userMapper.toModel(userRequestDto));
	}

	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email).orElse(null);
	}
	
	public void delete(User user) {
		this.userRepository.delete(user);
	}

	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Transactional
	public Transaction transfer(Long payerId, Long payeeId, BigDecimal ammount) throws BusinessException {
		User payer = this.userRepository.findById(payerId)
				.orElseThrow(() -> new BusinessException("Payer not found"));
		User payee = this.userRepository.findById(payeeId)
				.orElseThrow(() -> new BusinessException("Payee not found"));

		this.clearValidations();
		this.addValidation(new OnlyUserCanTransferValidation(payer));
		this.addValidation(new PayerHasBalanceValidation(payer, ammount));
		this.addValidation(new AuthorizeTransferValidation());
		performValidations();

		Transaction transaction = new Transaction(payer, payee, ammount);
		payer.setBalance(payer.getBalance().subtract(ammount));
		payee.setBalance(payee.getBalance().add(ammount));
		this.userRepository.saveAll(List.of(payee, payer));
		Transaction toReturn = this.transactionRepository.save(transaction);

		this.notificationService.notifyUser(payer);
		this.notificationService.notifyUser(payee);

		return toReturn;
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username).orElseThrow(
        		() -> new BusinessException("Invalid email or password"));
    }
}
