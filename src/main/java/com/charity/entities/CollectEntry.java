package com.charity.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.charity.dto.CouponAmountDTO;
import com.charity.util.GeneralUtils;

// سند التحصيل او سند الاستلام
@Entity
public class CollectEntry extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6109168077355672632L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User creator;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	private User delegate;

	@ManyToOne(fetch = FetchType.LAZY)
	private Branch branch;

	private BigDecimal amount;

	private int numberOfReceipts;

	@Column(columnDefinition = "bit default 0")
	private boolean collectedByFinance = false; // هل تم الترحيل للمالية ام لا

	@OneToMany(mappedBy = "collectEntry", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CollectEntryReceipt> receiptsList = new ArrayList<CollectEntryReceipt>();

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCollectedByFinance;

	@Transient
	private String dateCreatedFormatted;

	@Transient
	private List<CouponAmountDTO> couponsList = new ArrayList<CouponAmountDTO>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public User getDelegate() {
		return delegate;
	}

	public void setDelegate(User delegate) {
		this.delegate = delegate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getNumberOfReceipts() {
		return numberOfReceipts;
	}

	public void setNumberOfReceipts(int numberOfReceipts) {
		this.numberOfReceipts = numberOfReceipts;
	}

	public List<CollectEntryReceipt> getReceiptsList() {
		return receiptsList;
	}

	public void setReceiptsList(List<CollectEntryReceipt> receiptsList) {
		this.receiptsList = receiptsList;
	}

	public boolean isCollectedByFinance() {
		return collectedByFinance;
	}

	public void setCollectedByFinance(boolean collectedByFinance) {
		this.collectedByFinance = collectedByFinance;
	}

	public Date getDateCollectedByFinance() {
		return dateCollectedByFinance;
	}

	public void setDateCollectedByFinance(Date dateCollectedByFinance) {
		this.dateCollectedByFinance = dateCollectedByFinance;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getDateCreatedFormatted() {
		dateCreatedFormatted = GeneralUtils.formatDateOnly(dateCreated);
		return dateCreatedFormatted;
	}

	public void setDateCreatedFormatted(String dateCreatedFormatted) {
		this.dateCreatedFormatted = dateCreatedFormatted;
	}

	public List<CouponAmountDTO> getCouponsList() {
		return couponsList;
	}

	public void setCouponsList(List<CouponAmountDTO> couponsList) {
		this.couponsList = couponsList;
	}

}
