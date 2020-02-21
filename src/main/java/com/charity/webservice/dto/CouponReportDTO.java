package com.charity.webservice.dto;

import java.math.BigDecimal;

import com.charity.entities.Coupon;

public class CouponReportDTO {

	private Long id;
	private String name;
	private BigDecimal amount = new BigDecimal(0);

	public CouponReportDTO() {

	}

	public CouponReportDTO(Coupon coupon, BigDecimal amount) {
		this.id = coupon.getId();
		this.name = coupon.getName();
		this.amount = amount;
	}

	public CouponReportDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CouponReportDTO other = (CouponReportDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
