package com.charity.dto;

import com.charity.entities.Coupon;
import com.charity.enums.ErrorCodeEnum;

public class ValidateCreateReceiptResult {

	private ErrorCodeEnum errorCode;
	private Coupon coupon;

	public ValidateCreateReceiptResult() {

	}

	public ValidateCreateReceiptResult(ErrorCodeEnum errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public ValidateCreateReceiptResult(ErrorCodeEnum errorCode, Coupon coupon) {
		super();
		this.errorCode = errorCode;
		this.coupon = coupon;
	}

	public ErrorCodeEnum getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCodeEnum errorCode) {
		this.errorCode = errorCode;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

}
