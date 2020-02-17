package com.charity.dto;

import java.math.BigDecimal;

import com.charity.entities.User;

public class DelegateIncomeReportDTO {

	private String delegateName;
	private String delegateNumber;
	private long delegateId;
	private BigDecimal cashAmount = new BigDecimal(0);
	private BigDecimal creditCardAmount = new BigDecimal(0);
	private BigDecimal chequeAmount = new BigDecimal(0);
	private BigDecimal bankTransferAmount = new BigDecimal(0);
	private BigDecimal totalAmount = new BigDecimal(0);

	public DelegateIncomeReportDTO(User delegate) {
		this.delegateName = delegate.getDisplayName();
		this.delegateNumber = delegate.getId().toString();
		this.delegateId = delegate.getId();
	}

	public String getDelegateName() {
		return delegateName;
	}

	public void setDelegateName(String delegateName) {
		this.delegateName = delegateName;
	}

	public String getDelegateNumber() {
		return delegateNumber;
	}

	public void setDelegateNumber(String delegateNumber) {
		this.delegateNumber = delegateNumber;
	}

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

	public BigDecimal getBankTransferAmount() {
		return bankTransferAmount;
	}

	public void setBankTransferAmount(BigDecimal bankTransferAmount) {
		this.bankTransferAmount = bankTransferAmount;
	}

	public BigDecimal getTotalAmount() {
		totalAmount = cashAmount.add(chequeAmount).add(creditCardAmount).add(bankTransferAmount);
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(long delegateId) {
		this.delegateId = delegateId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (delegateId ^ (delegateId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DelegateIncomeReportDTO other = (DelegateIncomeReportDTO) obj;
		if (delegateId != other.delegateId)
			return false;
		return true;
	}

}
