package com.simplepicpay.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplepicpay.shared.Constants;
import com.simplepicpay.shared.DateUtil;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7079556927665964169L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "creation_date", nullable = false)
	@JsonProperty("createdAt")
	private LocalDateTime creationDate = LocalDateTime.now();

	public BaseEntity() {
		super();
		this.creationDate = LocalDateTime.now();
	}

	public BaseEntity(Long id, LocalDateTime creationDate) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		if (creationDate == null) {
			this.creationDate = LocalDateTime.now();
		}
	}

	@JsonIgnore
	public String getAgeString() {
		if (this.creationDate == null) {
			return "";
		}
		return DateUtil.getDuration(creationDate);
	}

	@JsonIgnore
	public String getCreationDateString() {
		if (this.creationDate == null) {
			return "";
		}
		return this.creationDate.format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT));
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BaseEntity)) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
		return Objects.equals(this.id, other.id);
	}
}
