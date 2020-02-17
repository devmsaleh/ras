package com.charity.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.charity.entities.CollectEntry;
import com.charity.entities.FinanceEntry;

public class CollectionReportDTO {

	private Long collectionId;
	private BigDecimal amount;
	private String date;
	private String delegateName;
	private String accountantName;
	private List<CouponAmountDTO> couponsList = new ArrayList<CouponAmountDTO>();

	public CollectionReportDTO() {

	}

	public CollectionReportDTO(FinanceEntry financeEntry) {
		this.collectionId = financeEntry.getId();
		this.amount = financeEntry.getAmount();
		this.date = financeEntry.getDateCreatedFormatted();
		this.accountantName = financeEntry.getAccountant().getName();
	}

	public CollectionReportDTO(CollectEntry collectEntry) {
		this.collectionId = collectEntry.getId();
		this.amount = collectEntry.getAmount();
		this.date = collectEntry.getDateCreatedFormatted();
		this.delegateName = collectEntry.getDelegate().getDisplayName();
	}

	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDelegateName() {
		return delegateName;
	}

	public void setDelegateName(String delegateName) {
		this.delegateName = delegateName;
	}

	public List<CouponAmountDTO> getCouponsList() {
		return couponsList;
	}

	public void setCouponsList(List<CouponAmountDTO> couponsList) {
		this.couponsList = couponsList;
	}

	public String getAccountantName() {
		return accountantName;
	}

	public void setAccountantName(String accountantName) {
		this.accountantName = accountantName;
	}

}
