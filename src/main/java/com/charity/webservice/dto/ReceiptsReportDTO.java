package com.charity.webservice.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReceiptsReportDTO {

	private List<ReceiptDetailDTO> cashList = new ArrayList<ReceiptDetailDTO>();
	private List<ReceiptDetailDTO> creditCardList = new ArrayList<ReceiptDetailDTO>();
	private List<ReceiptDetailDTO> chequeList = new ArrayList<ReceiptDetailDTO>();
	private List<ReceiptDetailDTO> bankTransferList = new ArrayList<ReceiptDetailDTO>();
	private BigDecimal cashAmount = new BigDecimal(0);
	private BigDecimal creditCardAmount = new BigDecimal(0);
	private BigDecimal chequeAmount = new BigDecimal(0);
	private BigDecimal bankTransferAmount = new BigDecimal(0);
	private BigDecimal totalAmount = new BigDecimal(0);

	public List<ReceiptDetailDTO> getCashList() {
		return cashList;
	}

	public void setCashList(List<ReceiptDetailDTO> cashList) {
		this.cashList = cashList;
	}

	public List<ReceiptDetailDTO> getCreditCardList() {
		return creditCardList;
	}

	public void setCreditCardList(List<ReceiptDetailDTO> creditCardList) {
		this.creditCardList = creditCardList;
	}

	public List<ReceiptDetailDTO> getChequeList() {
		return chequeList;
	}

	public void setChequeList(List<ReceiptDetailDTO> chequeList) {
		this.chequeList = chequeList;
	}

	public List<ReceiptDetailDTO> getBankTransferList() {
		return bankTransferList;
	}

	public void setBankTransferList(List<ReceiptDetailDTO> bankTransferList) {
		this.bankTransferList = bankTransferList;
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
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

}
