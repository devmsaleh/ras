package com.charity.webservice.dto;

import java.math.BigDecimal;

public class NewCouponDTO {

	private Long couponTypeId;
	private String couponTypeName;
	private BigDecimal amount;
	private String notes;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getCouponTypeId() {
		return couponTypeId;
	}

	public void setCouponTypeId(Long couponTypeId) {
		this.couponTypeId = couponTypeId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCouponTypeName() {
		return couponTypeName;
	}

	public void setCouponTypeName(String couponTypeName) {
		this.couponTypeName = couponTypeName;
	}

}
