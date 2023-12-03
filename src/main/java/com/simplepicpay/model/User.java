package com.simplepicpay.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_user")
@JsonIgnoreProperties({"accountNonExpired", "enabled", "credentialsNonExpired", 
	"username", "authorities", "accountNonLocked"})
public class User extends BaseEntity implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1753055857941816318L;

	@Column(name = "document", unique = true, nullable = false)
	private String document;
	
	@Column(name = "name", nullable = false)
	private String name;

    @Column(name = "email", unique = true, nullable = false)
	private String email;

    @Column(name = "user_passwd", nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
	private String password;

    @Column(name = "balance", nullable = false)
	private BigDecimal balance;

    @Enumerated(EnumType.STRING)
	@Column(name = "user_type", nullable = false)
	private UserType userType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole userRole;

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> toReturn = List.of(
				new SimpleGrantedAuthority(UserRole.USER.toString()));
		if (this.userRole.equals(UserRole.ADMIN)) {
			toReturn.add(new SimpleGrantedAuthority(UserRole.ADMIN.toString()));
		}
		return toReturn;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", balance=" + balance
				+ ", document=" + document + ", userType=" + userType + ", role=" + userRole + "]";
	}
}
