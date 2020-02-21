package com.charity.webservice.dto;

import java.math.BigDecimal;

import com.charity.enums.CouponTypeEnum;

public class CouponTypeDTO {

	private String id;

	private String name;

	private boolean mustEnterDonator;

	private BigDecimal value;

	private BigDecimal minimumAmount;

	private String qrCode;

	private int priority;

	private CouponTypeEnum type = CouponTypeEnum.NORMAL;

	private boolean favorite;

	private int version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isMustEnterDonator() {
		return mustEnterDonator;
	}

	public void setMustEnterDonator(boolean mustEnterDonator) {
		this.mustEnterDonator = mustEnterDonator;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(BigDecimal minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public CouponTypeEnum getType() {
		return type;
	}

	public void setType(CouponTypeEnum type) {
		this.type = type;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
