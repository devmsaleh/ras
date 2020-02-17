package com.charity.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class FinanceEntryCollectEntry implements Serializable {

	private static final long serialVersionUID = 4543662932809037238L;

	@EmbeddedId
	private FinanceEntryCollectEntryPK id;

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@MapsId("financeEntryId")
	private FinanceEntry financeEntry;

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@MapsId("collectEntryId")
	private CollectEntry collectEntry;

	private BigDecimal amount;

	public FinanceEntryCollectEntry() {

	}

	public FinanceEntryCollectEntry(FinanceEntry financeEntry, CollectEntry collectEntry) {
		this.id = new FinanceEntryCollectEntryPK(financeEntry.getId(), collectEntry.getId());
		this.financeEntry = financeEntry;
		this.collectEntry = collectEntry;
		this.amount = collectEntry.getAmount();
	}

	public FinanceEntryCollectEntryPK getId() {
		return id;
	}

	public void setId(FinanceEntryCollectEntryPK id) {
		this.id = id;
	}

	public FinanceEntry getFinanceEntry() {
		return financeEntry;
	}

	public void setFinanceEntry(FinanceEntry financeEntry) {
		this.financeEntry = financeEntry;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
