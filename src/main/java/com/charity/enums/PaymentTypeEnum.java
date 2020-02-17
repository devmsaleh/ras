package com.charity.enums;

public enum PaymentTypeEnum {

	CASH("نقدي"), CHEQUE("شيك"), CREDIT("بطاقة الائتمان"), BANK_TRANSFER("تحويل بنكي");

	private String label;

	public String getLabel() {
		return label;
	}

	private PaymentTypeEnum(String label) {
		this.label = label;
	}

}
