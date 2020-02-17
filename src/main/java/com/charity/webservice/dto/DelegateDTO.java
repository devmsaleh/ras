package com.charity.webservice.dto;

import java.math.BigDecimal;

public class DelegateDTO {

	private String id;
	private String number;
	private String name;
	private String userName;
	private String expiryDate;
	private BigDecimal maxLimit;
	private boolean admin;
	private String startDate;
	private String token;
	private boolean charityBox;
	private boolean showCollectors;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public boolean isCharityBox() {
		return charityBox;
	}

	public void setCharityBox(boolean charityBox) {
		this.charityBox = charityBox;
	}

	public boolean isShowCollectors() {
		return showCollectors;
	}

	public void setShowCollectors(boolean showCollectors) {
		this.showCollectors = showCollectors;
	}

}
