package com.charity.webservice.dto;

import java.util.ArrayList;
import java.util.List;

import com.charity.enums.PaymentTypeEnum;

public class ReceiptDTO {

	private Long delegateId;

	private PaymentTypeEnum paymentType = PaymentTypeEnum.CASH;

	private List<NewCouponDTO> couponsList = new ArrayList<NewCouponDTO>();

	private ReceiptPaymentDTO receiptPaymentDTO = new ReceiptPaymentDTO();

	private BenefactorDTO benefactor = new BenefactorDTO();

	public Long getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(Long delegateId) {
		this.delegateId = delegateId;
	}

	public List<NewCouponDTO> getCouponsList() {
		return couponsList;
	}

	public void setCouponsList(List<NewCouponDTO> couponsList) {
		this.couponsList = couponsList;
	}

	public ReceiptPaymentDTO getReceiptPaymentDTO() {
		return receiptPaymentDTO;
	}

	public void setReceiptPaymentDTO(ReceiptPaymentDTO receiptPaymentDTO) {
		this.receiptPaymentDTO = receiptPaymentDTO;
	}

	public PaymentTypeEnum getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}

	public BenefactorDTO getBenefactor() {
		return benefactor;
	}

	public void setBenefactor(BenefactorDTO benefactor) {
		this.benefactor = benefactor;
	}

}
