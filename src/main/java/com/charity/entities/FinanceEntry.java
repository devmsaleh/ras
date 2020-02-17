package com.charity.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.charity.util.GeneralUtils;

@Entity
public class FinanceEntry extends BaseEntity implements Serializable {

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
	private Accountant accountant;

	private BigDecimal amount;

	@OneToMany(mappedBy = "collectEntry", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<FinanceEntryCollectEntry> collectEntryList = new ArrayList<FinanceEntryCollectEntry>();

	@Transient
	private String dateCreatedFormatted;

	@Transient
	private List<ReceiptDetail> receiptDetailsList = new ArrayList<ReceiptDetail>();

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public List<FinanceEntryCollectEntry> getCollectEntryList() {
		return collectEntryList;
	}

	public void setCollectEntryList(List<FinanceEntryCollectEntry> collectEntryList) {
		this.collectEntryList = collectEntryList;
	}

	public Accountant getAccountant() {
		return accountant;
	}

	public void setAccountant(Accountant accountant) {
		this.accountant = accountant;
	}

	public String getDateCreatedFormatted() {
		dateCreatedFormatted = GeneralUtils.formatDateOnly(dateCreated);
		return dateCreatedFormatted;
	}

	public void setDateCreatedFormatted(String dateCreatedFormatted) {
		this.dateCreatedFormatted = dateCreatedFormatted;
	}

	public List<ReceiptDetail> getReceiptDetailsList() {
		return receiptDetailsList;
	}

	public void setReceiptDetailsList(List<ReceiptDetail> receiptDetailsList) {
		this.receiptDetailsList = receiptDetailsList;
	}

}
