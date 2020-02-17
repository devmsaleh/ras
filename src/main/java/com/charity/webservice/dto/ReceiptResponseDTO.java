package com.charity.webservice.dto;

public class ReceiptResponseDTO {

	private Long receiptId;

	public ReceiptResponseDTO() {

	}

	public ReceiptResponseDTO(Long receiptId) {
		this.receiptId = receiptId;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

}
