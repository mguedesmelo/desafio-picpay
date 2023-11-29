package com.simplepicpay.service;

import java.util.ArrayList;
import java.util.List;

import com.simplepicpay.exception.BusinessException;
import com.simplepicpay.validation.BaseValidation;

public abstract class BaseService {
	private List<BaseValidation> validationList = new ArrayList<BaseValidation>(0);

	protected void addValidation(BaseValidation validation) {
		this.validationList.add(validation);
	}

	protected void clearValidations() {
		this.validationList.clear();
	}
	
	protected void performValidations() throws BusinessException {
		for (BaseValidation v : this.validationList) {
			if (!v.valid()) {
				throw new BusinessException(v.getMessage());
			}
		};
	}
}
