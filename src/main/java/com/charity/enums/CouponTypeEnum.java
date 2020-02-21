package com.charity.enums;

public enum CouponTypeEnum {

	NORMAL(0), YEARLY(1), QUICK_PAY(2);

	private Integer value;

	public Integer getValue() {
		return value;
	}

	private CouponTypeEnum(Integer value) {
		this.value = value;
	}

}
