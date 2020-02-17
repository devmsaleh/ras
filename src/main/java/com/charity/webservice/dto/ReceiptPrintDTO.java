package com.charity.webservice.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.charity.enums.PaymentTypeEnum;

public class ReceiptPrintDTO {

	private String receiptDate;
	private String receiptNumber;
	private String paymentType = PaymentTypeEnum.CASH.toString();
	private String paymentTypeDisplay = PaymentTypeEnum.CASH.toString();
	private String donatorName;
	private String donatorMobile;
	private List<ReceiptDetailDTO> details = new ArrayList<ReceiptDetailDTO>();
	private BigDecimal totalAmount = new BigDecimal(0);
	private int numberOfPrints;

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getDonatorName() {
		return donatorName;
	}

	public void setDonatorName(String donatorName) {
		this.donatorName = donatorName;
	}

	public String getDonatorMobile() {
		return donatorMobile;
	}

	public void setDonatorMobile(String donatorMobile) {
		this.donatorMobile = donatorMobile;
	}

	public List<ReceiptDetailDTO> getDetails() {
		return details;
	}

	public void setDetails(List<ReceiptDetailDTO> details) {
		this.details = details;
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

	public int getNumberOfPrints() {
		return numberOfPrints;
	}

	public void setNumberOfPrints(int numberOfPrints) {
		this.numberOfPrints = numberOfPrints;
	}

}
