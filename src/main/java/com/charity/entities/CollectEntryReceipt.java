package com.charity.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CollectEntryReceipt implements Serializable {

	private static final long serialVersionUID = 4543662932809037238L;

	@EmbeddedId
	private CollectEntryReceiptPK id;

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@MapsId("collectEntryId")
	private CollectEntry collectEntry;

	@ManyToOne(optional = false)
	@MapsId("receiptId")
	private Receipt receipt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date receiptDate;

	private BigDecimal amount;

	public CollectEntryReceipt() {

	}

	public CollectEntryReceipt(CollectEntry collectEntry, Receipt receipt) {
		this.id = new CollectEntryReceiptPK(collectEntry.getId(), receipt.getId());
		this.collectEntry = collectEntry;
		this.receipt = receipt;
		this.receiptDate = receipt.getReceiptDate();
		this.amount = receipt.getTotalAmount();
	}

	public CollectEntryReceiptPK getId() {
		return id;
	}

	public void setId(CollectEntryReceiptPK id) {
		this.id = id;
	}

	public CollectEntry getCollectEntry() {
		return collectEntry;
	}

	public void setCollectEntry(CollectEntry collectEntry) {
		this.collectEntry = collectEntry;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
