package com.charity.entities;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract Long getId();

	public abstract void setId(Long id);

	@Override
	public int hashCode() {
		return (getId() != null) ? (getClass().getSimpleName().hashCode() + getId().hashCode()) : super.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		return (other != null && getId() != null && other.getClass().isAssignableFrom(getClass())
				&& getClass().isAssignableFrom(other.getClass())) ? getId().equals(((BaseEntity) other).getId())
						: (other == this);
	}

	@Override
	public String toString() {
		return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
	}

}
