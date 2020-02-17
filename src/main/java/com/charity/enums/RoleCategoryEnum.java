package com.charity.enums;

public enum RoleCategoryEnum {

	ADMIN("إعدادات النظام"), EMPLOYEE("استلام وتسليم المبالغ"), REPORT("التقارير"), LOOKUPS("إعدادات الفروع والمواقع");

	private String label;

	private RoleCategoryEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
