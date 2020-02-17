package com.charity.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserRolePK implements Serializable {

	private static final long serialVersionUID = -7839573088796277135L;

	private Long userId;

	private Long roleId;

	private Long branchId;

	public UserRolePK() {

	}

	public UserRolePK(Long userId, Long roleId, Long branchId) {
		this.userId = userId;
		this.roleId = roleId;
		this.branchId = branchId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		UserRolePK other = (UserRolePK) obj;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
