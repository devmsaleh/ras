package com.charity.webservice.dto;

public class ReceiptPaymentDTO {

	private String creditCardTransactionNumber;
	private String chequeNumber;
	private String chequeDate;
	private String bankCode;
	private String deductionNumber;
	private String accountName;
	private String accountNumber;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCreditCardTransactionNumber() {
		return creditCardTransactionNumber;
	}

	public void setCreditCardTransactionNumber(String creditCardTransactionNumber) {
		this.creditCardTransactionNumber = creditCardTransactionNumber;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public String getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getDeductionNumber() {
		return deductionNumber;
	}

	public void setDeductionNumber(String deductionNumber) {
		this.deductionNumber = deductionNumber;
	}

}
