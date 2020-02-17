package com.charity.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class UserRole implements Serializable {

	private static final long serialVersionUID = -4702668219237906064L;

	@EmbeddedId
	private UserRolePK id;

	@ManyToOne(optional = false)
	@MapsId("userId")
	private User user;

	@ManyToOne(optional = false)
	@MapsId("roleId")
	private Role role;

	@ManyToOne
	@MapsId("branchId")
	private Branch branch;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	private User creator;

	public UserRole() {

	}

	public UserRole(User user, Role role) {
		this.id = new UserRolePK(user.getId(), role.getId(), null);
		this.user = user;
		this.role = role;
	}

	public UserRole(User user, Role role, Branch branch) {
		this.id = new UserRolePK(user.getId(), role.getId(), branch.getId());
		this.user = user;
		this.role = role;
		this.branch = branch;
	}

	public UserRolePK getId() {
		return id;
	}

	public void setId(UserRolePK id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

}
