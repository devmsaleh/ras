package com.charity.enums;

public enum UserTypeEnum {

	ADMIN("ADMIN"), DELEGATE("DELEGATE"), EMPLOYEE("EMPLOYEE"), BENEFACTOR("BENEFACTOR");

	private String value;

	public String getValue() {
		return value;
	}

	private UserTypeEnum(String value) {
		this.value = value;
	}

}
