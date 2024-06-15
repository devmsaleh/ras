package com.charity.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.charity.entities.Receipt;
import com.charity.enums.PaymentTypeEnum;

public class CollectionDTO {

	private List<Receipt> receiptsList = new ArrayList<Receipt>();
	private List<Receipt> selectedReceiptsList = new ArrayList<Receipt>();
	private BigDecimal cashAmount = new BigDecimal(0);
	private BigDecimal creditCardAmount = new BigDecimal(0);
	private BigDecimal chequeAmount = new BigDecimal(0);
	private BigDecimal bankTransferAmount = new BigDecimal(0);
	private BigDecimal totalAmount = new BigDecimal(0);

	public CollectionDTO(List<Receipt> receiptsList) {
		this.receiptsList = receiptsList;
		for (Receipt receipt : receiptsList) {
			if (receipt.getPaymentType().equals(PaymentTypeEnum.BANK_TRANSFER)) {
				bankTransferAmount = bankTransferAmount.add(receipt.getTotalAmount());
			} else if (receipt.getPaymentType().equals(PaymentTypeEnum.CASH)) {
				cashAmount = cashAmount.add(receipt.getTotalAmount());
			} else if (receipt.getPaymentType().equals(PaymentTypeEnum.CHEQUE)) {
				chequeAmount = chequeAmount.add(receipt.getTotalAmount());
			} else if (receipt.getPaymentType().equals(PaymentTypeEnum.CREDIT)) {
				creditCardAmount = creditCardAmount.add(receipt.getTotalAmount());
			}
//			for (ReceiptDetail receiptDetail : receipt.getReceiptDetailsList()) {
//				if (receiptDetail.getPaymentType().equals(PaymentTypeEnum.BANK_TRANSFER)) {
//					bankTransferAmount = bankTransferAmount.add(receiptDetail.getAmount());
//				} else if (receiptDetail.getPaymentType().equals(PaymentTypeEnum.CASH)) {
//					cashAmount = cashAmount.add(receiptDetail.getAmount());
//				} else if (receiptDetail.getPaymentType().equals(PaymentTypeEnum.CHEQUE)) {
//					chequeAmount = chequeAmount.add(receiptDetail.getAmount());
//				} else if (receiptDetail.getPaymentType().equals(PaymentTypeEnum.CREDIT)) {
//					creditCardAmount = creditCardAmount.add(receiptDetail.getAmount());
//				}
//			}
		}

		totalAmount = (cashAmount.add(chequeAmount).add(creditCardAmount).add(bankTransferAmount));
	}

	public CollectionDTO() {

	}

	public List<Receipt> getReceiptsList() {
		return receiptsList;
	}

	public void setReceiptsList(List<Receipt> receiptsList) {
		this.receiptsList = receiptsList;
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

	public List<Receipt> getSelectedReceiptsList() {
		return selectedReceiptsList;
	}

	public void setSelectedReceiptsList(List<Receipt> selectedReceiptsList) {
		this.selectedReceiptsList = selectedReceiptsList;
	}

}
