package com.charity.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CollectEntryReceiptPK implements Serializable {

	private static final long serialVersionUID = 8255618043758671522L;

	private Long collectEntryId;

	private Long receiptId;

	public CollectEntryReceiptPK() {

	}

	public CollectEntryReceiptPK(Long collectEntryId, Long receiptId) {
		this.collectEntryId = collectEntryId;
		this.receiptId = receiptId;
	}

	public Long getCollectEntryId() {
		return collectEntryId;
	}

	public void setCollectEntryId(Long collectEntryId) {
		this.collectEntryId = collectEntryId;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collectEntryId == null) ? 0 : collectEntryId.hashCode());
		result = prime * result + ((receiptId == null) ? 0 : receiptId.hashCode());
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
		CollectEntryReceiptPK other = (CollectEntryReceiptPK) obj;
		if (collectEntryId == null) {
			if (other.collectEntryId != null)
				return false;
		} else if (!collectEntryId.equals(other.collectEntryId))
			return false;
		if (receiptId == null) {
			if (other.receiptId != null)
				return false;
		} else if (!receiptId.equals(other.receiptId))
			return false;
		return true;
	}

}
