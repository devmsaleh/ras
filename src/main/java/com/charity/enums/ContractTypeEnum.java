package com.charity.enums;

public enum ContractTypeEnum {

	PERMANENT("PERMANENT"), TEMPORARY("TEMPORARY");

	private String value;

	public String getValue() {
		return value;
	}

	private ContractTypeEnum(String value) {
		this.value = value;
	}

}
