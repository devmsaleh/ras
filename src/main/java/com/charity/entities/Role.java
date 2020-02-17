package com.charity.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.charity.enums.RoleCategoryEnum;

@Entity
public class Role extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7080255148076085369L;
	@Id
	@Column(nullable = false, unique = true)
	private Long id;
	@Column(nullable = false, unique = true)
	private String name;

	private String description;

	@Enumerated(EnumType.STRING)
	private RoleCategoryEnum category;

	@Column(columnDefinition = "bit default 0")
	private boolean hidden = false;

	@OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
	private Set<UserRole> userRoles = new HashSet<UserRole>();

	public Role() {

	}

	public Role(Long id) {
		this.id = id;
	}

	public Role(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RoleCategoryEnum getCategory() {
		return category;
	}

	public void setCategory(RoleCategoryEnum category) {
		this.category = category;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

}
