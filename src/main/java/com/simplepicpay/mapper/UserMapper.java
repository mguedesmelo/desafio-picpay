package com.simplepicpay.mapper;

import org.springframework.stereotype.Component;

import com.simplepicpay.dto.UserRequestDto;
import com.simplepicpay.model.User;
import com.simplepicpay.model.UserRole;
import com.simplepicpay.model.UserType;

/**
 * Class to map the User entity to the UserRequestDTO and vice-versa.
 * ModelMapper currently does not support record types.
 */
@Component
public class UserMapper extends BaseMapper {
	public UserRequestDto map(User user) {
		if (user == null) {
			return null;
		}
		return new UserRequestDto(user.getFirstName(), user.getLastName(), user.getEmail(), 
				user.getPassword(), user.getBalance(), user.getDocument(), 
				user.getUserType().getType(), user.getUserRole().getRole());
	}

	public User toModel(UserRequestDto userRequestDto) {
		User toReturn = new User();
		toReturn.setFirstName(userRequestDto.firstName());
		toReturn.setLastName(userRequestDto.lastName());
		toReturn.setEmail(userRequestDto.email());
		toReturn.setPassword(userRequestDto.password());
		toReturn.setBalance(userRequestDto.balance());
		toReturn.setDocument(userRequestDto.document());
		toReturn.setUserType(this.convertUserTypeValue(userRequestDto.userType()));
		toReturn.setUserRole(this.convertUserRoleValue(userRequestDto.userRole()));

		return toReturn;
	}
	
	private UserType convertUserTypeValue(String value) {
		UserType toReturn = null;
		if (value == null) {
			return null;
		}
		toReturn = UserType.valueOf(value);
		if (toReturn == null) {
			throw new IllegalArgumentException("Invalid UserType.");
		}
		return toReturn;
	}
	
	private UserRole convertUserRoleValue(String value) {
		UserRole toReturn = null;
		if (value == null) {
			return null;
		}
		toReturn = UserRole.valueOf(value);
		if (toReturn == null) {
			throw new IllegalArgumentException("Invalid UserRole.");
		}
		return toReturn;
	}
}