package com.charity.enums;

public enum TransactionTypeEnum {

	COUPON("COUPON"), PROJECT_NEW("PROJECT_NEW"), PROJECT_OLD("PROJECT_OLD"), SPONSORSHIP_NEW("SPONSORSHIP_NEW"),
	SPONSORSHIP_OLD("SPONSORSHIP_OLD");

	private String value;

	public String getValue() {
		return value;
	}

	private TransactionTypeEnum(String value) {
		this.value = value;
	}

}
