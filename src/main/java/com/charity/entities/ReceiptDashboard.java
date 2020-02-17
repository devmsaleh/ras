package com.charity.entities;

import java.math.BigDecimal;

public class ReceiptDashboard {

	private Receipt receipt;
	private User delegate;
	private BigDecimal delegateNotCollectedAmount;

	public ReceiptDashboard() {

	}

	public ReceiptDashboard(Receipt receipt, BigDecimal delegateNotCollectedAmount) {
		this.receipt = receipt;
		this.delegate = receipt.getDelegate();
		this.delegateNotCollectedAmount = delegateNotCollectedAmount;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public BigDecimal getDelegateNotCollectedAmount() {
		return delegateNotCollectedAmount;
	}

	public void setDelegateNotCollectedAmount(BigDecimal delegateNotCollectedAmount) {
		this.delegateNotCollectedAmount = delegateNotCollectedAmount;
	}

	public User getDelegate() {
		return delegate;
	}

	public void setDelegate(User delegate) {
		this.delegate = delegate;
	}

}
