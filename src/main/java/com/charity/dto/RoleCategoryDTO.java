package com.charity.dto;

import java.util.ArrayList;
import java.util.List;

import com.charity.entities.Role;
import com.charity.enums.RoleCategoryEnum;

public class RoleCategoryDTO {

	private RoleCategoryEnum category;
	private List<Role> rolesList = new ArrayList<Role>();
	private List<Role> selectedRolesList = new ArrayList<Role>();

	public RoleCategoryDTO() {

	}

	public RoleCategoryDTO(RoleCategoryEnum category) {
		this.category = category;
	}

	public RoleCategoryEnum getCategory() {
		return category;
	}

	public void setCategory(RoleCategoryEnum category) {
		this.category = category;
	}

	public List<Role> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<Role> rolesList) {
		this.rolesList = rolesList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
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
		RoleCategoryDTO other = (RoleCategoryDTO) obj;
		if (category != other.category)
			return false;
		return true;
	}

	public List<Role> getSelectedRolesList() {
		return selectedRolesList;
	}

	public void setSelectedRolesList(List<Role> selectedRolesList) {
		this.selectedRolesList = selectedRolesList;
	}

}
