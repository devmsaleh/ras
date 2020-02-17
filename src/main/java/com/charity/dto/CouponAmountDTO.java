package com.charity.dto;

import java.math.BigDecimal;

import com.charity.entities.Coupon;

public class CouponAmountDTO {

	private Long couponId;
	private String couponName;
	private BigDecimal amount;

	public CouponAmountDTO() {

	}

	public CouponAmountDTO(Coupon coupon, BigDecimal amount) {
		this.couponId = coupon.getId();
		this.couponName = coupon.getName();
		this.amount = amount;
	}

	public CouponAmountDTO(String couponName, BigDecimal amount) {
		this.couponName = couponName;
		this.amount = amount;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((couponId == null) ? 0 : couponId.hashCode());
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
		CouponAmountDTO other = (CouponAmountDTO) obj;
		if (couponId == null) {
			if (other.couponId != null)
				return false;
		} else if (!couponId.equals(other.couponId))
			return false;
		return true;
	}

}
