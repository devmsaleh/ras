package com.charity.enums;

public enum GenderTypeEnum {

	MALE("MALE"), FEMALE("FEMALE");

	private String value;

	public String getValue() {
		return value;
	}

	private GenderTypeEnum(String value) {
		this.value = value;
	}

}
