package com.simplepicpay.mapper;

import org.springframework.stereotype.Component;

import com.simplepicpay.dto.UserRequestDto;
import com.simplepicpay.model.User;
import com.simplepicpay.model.UserRole;
import com.simplepicpay.model.UserType;
import com.simplepicpay.shared.StringUtil;

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
		return new UserRequestDto(user.getId(), user.getName(), user.getEmail(), 
				user.getPassword(), user.getBalance(), user.getDocument(), 
				user.getUserType().getType(), user.getUserRole().getRole());
	}

	public User toModel(UserRequestDto userRequestDto) {
		User toReturn = new User();
		toReturn.setName(userRequestDto.name());
		toReturn.setEmail(userRequestDto.email());
		toReturn.setPassword(userRequestDto.password());
		toReturn.setBalance(userRequestDto.balance());
		toReturn.setDocument(userRequestDto.document());
		toReturn.setUserType(this.toUserType(userRequestDto.userType()));
		toReturn.setUserRole(this.toUserRole(userRequestDto.userRole()));

		return toReturn;
	}

	/**
	 * Convert from string to UserType enum.
	 * @param value The user type string
	 * @return The corresponding UserType enum item
	 */
	private UserType toUserType(String value) {
		UserType toReturn = null;
		if (StringUtil.isNullOrEmpty(value)) {
			return null;
		}
		toReturn = UserType.valueOf(value);
		if (toReturn == null) {
			throw new IllegalArgumentException("Invalid User Type.");
		}
		return toReturn;
	}

	/**
	 * Convert from string to UserRole enum.
	 * @param value The user role string
	 * @return The corresponding UserRole enum item
	 */
	private UserRole toUserRole(String value) {
		UserRole toReturn = null;
		if (StringUtil.isNullOrEmpty(value)) {
			return null;
		}
		toReturn = UserRole.valueOf(value);
		if (toReturn == null) {
			throw new IllegalArgumentException("Invalid User Role.");
		}
		return toReturn;
	}
}
