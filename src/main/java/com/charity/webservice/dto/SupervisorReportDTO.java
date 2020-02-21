package com.charity.webservice.dto;

import java.math.BigDecimal;

public class SupervisorReportDTO {

	private BigDecimal cashAmount = new BigDecimal(0);
	private BigDecimal creditCardAmount = new BigDecimal(0);
	private BigDecimal chequeAmount = new BigDecimal(0);
	private BigDecimal totalAmount = new BigDecimal(0);
	private int cashReceiptsCount;
	private int creditCardReceiptsCount;
	private int chequeReceiptsCount;
	private String date;

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getCreditCardAmount() {
		return creditCardAmount;
	}

	public void setCreditCardAmount(BigDecimal creditCardAmount) {
		this.creditCardAmount = creditCardAmount;
	}

	public BigDecimal getChequeAmount() {
		return chequeAmount;
	}

	public void setChequeAmount(BigDecimal chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getCashReceiptsCount() {
		return cashReceiptsCount;
	}

	public void setCashReceiptsCount(int cashReceiptsCount) {
		this.cashReceiptsCount = cashReceiptsCount;
	}

	public int getCreditCardReceiptsCount() {
		return creditCardReceiptsCount;
	}

	public void setCreditCardReceiptsCount(int creditCardReceiptsCount) {
		this.creditCardReceiptsCount = creditCardReceiptsCount;
	}

	public int getChequeReceiptsCount() {
		return chequeReceiptsCount;
	}

	public void setChequeReceiptsCount(int chequeReceiptsCount) {
		this.chequeReceiptsCount = chequeReceiptsCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
