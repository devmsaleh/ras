package com.charity.webservice.dto;

import java.math.BigDecimal;

public class ReceiptDetailDTO {

	private String receiptId;
	private String name;
	private BigDecimal amount;
	private String date;
	private String paymentType;
	private String paymentTypeDisplay;
	private String giftType;
	private BigDecimal giftAmount;
	private BigDecimal totalAmount = new BigDecimal(0);
	private String type;
	private String sponsorFor;

	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public String getPaymentTypeDisplay() {
		return paymentTypeDisplay;
	}

	public void setPaymentTypeDisplay(String paymentTypeDisplay) {
		this.paymentTypeDisplay = paymentTypeDisplay;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSponsorFor() {
		return sponsorFor;
	}

	public void setSponsorFor(String sponsorFor) {
		this.sponsorFor = sponsorFor;
	}

}
