package com.charity.enums;

public enum CurrencyEnum {

	SAR("SAR"), AED("AED"), EGP("EGP");

	private String value;

	CurrencyEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
