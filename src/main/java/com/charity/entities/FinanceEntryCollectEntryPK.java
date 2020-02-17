package com.charity.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class FinanceEntryCollectEntryPK implements Serializable {

	private static final long serialVersionUID = 8255618043758671522L;

	private Long financeEntryId;

	private Long collectEntryId;

	public FinanceEntryCollectEntryPK() {

	}

	public FinanceEntryCollectEntryPK(Long financeEntryId, Long collectEntryId) {
		this.financeEntryId = financeEntryId;
		this.collectEntryId = collectEntryId;
	}

	public Long getFinanceEntryId() {
		return financeEntryId;
	}

	public void setFinanceEntryId(Long financeEntryId) {
		this.financeEntryId = financeEntryId;
	}

	public Long getCollectEntryId() {
		return collectEntryId;
	}

	public void setCollectEntryId(Long collectEntryId) {
		this.collectEntryId = collectEntryId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collectEntryId == null) ? 0 : collectEntryId.hashCode());
		result = prime * result + ((financeEntryId == null) ? 0 : financeEntryId.hashCode());
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
		FinanceEntryCollectEntryPK other = (FinanceEntryCollectEntryPK) obj;
		if (collectEntryId == null) {
			if (other.collectEntryId != null)
				return false;
		} else if (!collectEntryId.equals(other.collectEntryId))
			return false;
		if (financeEntryId == null) {
			if (other.financeEntryId != null)
				return false;
		} else if (!financeEntryId.equals(other.financeEntryId))
			return false;
		return true;
	}

}
