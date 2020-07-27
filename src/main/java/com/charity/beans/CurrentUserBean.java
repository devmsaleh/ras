package com.charity.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.charity.entities.User;
import com.charity.entities.UserRole;
import com.charity.enums.BranchPermissionTypeEnum;
import com.charity.enums.RoleTypeEnum;
import com.charity.service.UserService;
import com.charity.util.Constants;

@Component("currentUserBean")
@Scope("session")
public class CurrentUserBean implements Serializable {

	private static final long serialVersionUID = -3493756271104748886L;

	private static final Logger log = LoggerFactory.getLogger(CurrentUserBean.class);

	private User user;

	@Autowired
	private UserService userService;

	private boolean admin;

	private boolean employee;

	private boolean hasAllBranches;

	private boolean hasReportsRole;

	@PostConstruct
	public void init() {
		try {

		} catch (Exception e) {
			log.error("Exception in init CurrentUserBean", e);
		}
	}

	private void loadUserData() {
		// log.info("########### loadUserData,user: " + user + ",getLoggedInUserId(): "
		// + getLoggedInUserId());
		if (user == null && !getLoggedInUserId().equalsIgnoreCase("anonymousUser")) {
			user = userService.findByUserNameIgnoreCase(getLoggedInUserId());
			// log.info("########## found user,roles: " + user.getRoles().size());
			admin = hasRole(RoleTypeEnum.ROLE_ADMIN);
			employee = hasRole(RoleTypeEnum.ROLE_EMPLOYEE);
			hasAllBranches = user.getBranchPermissionType() == BranchPermissionTypeEnum.ALL_BRANCHES;
			hasReportsRole = hasRole(RoleTypeEnum.ROLE_REPORT_BENEFACTORS)
					|| hasRole(RoleTypeEnum.ROLE_REPORT_COLLECTION_DELEGATES)
					|| hasRole(RoleTypeEnum.ROLE_REPORT_COUPONS_INCOME)
					|| hasRole(RoleTypeEnum.ROLE_REPORT_DELEGATES_INCOME)
					|| hasRole(RoleTypeEnum.ROLE_REPORT_FINANCE_DEPOSIT)
					|| hasRole(RoleTypeEnum.ROLE_REPORT_LOCATIONS_INCOME);
		}
	}

	private String getLoggedInUserId() {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return "anonymousUser";
		}
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal.getClass().equals(org.springframework.security.core.userdetails.User.class)) {
			org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) principal;
			return springUser.getUsername();
		} else
			return "anonymousUser";

	}

	public boolean hasRoleForAllBranches(RoleTypeEnum roleTypeEnum) {
		if (getUser() == null)
			return false;
		for (UserRole userRole : getUser().getRoles()) {
			if (userRole.getBranch().getId() == Constants.ALL_BRANCHES_ID
					&& userRole.getRole().getName().equalsIgnoreCase(roleTypeEnum.getName()))
				return true;
		}
		return false;
	}

	public boolean hasRole(RoleTypeEnum roleTypeEnum) {
		if (getUser() == null)
			return false;
		for (UserRole userRole : getUser().getRoles()) {
			if (userRole.getRole().getName().equalsIgnoreCase(roleTypeEnum.getName()))
				return true;
		}
		return false;
	}

	public User getUser() {
		loadUserData();
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isAdmin() {
		loadUserData();
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isEmployee() {
		return employee;
	}

	public void setEmployee(boolean employee) {
		this.employee = employee;
	}

	public boolean isHasAllBranches() {
		return hasAllBranches;
	}

	public void setHasAllBranches(boolean hasAllBranches) {
		this.hasAllBranches = hasAllBranches;
	}

	public boolean isHasReportsRole() {
		return hasReportsRole;
	}

	public void setHasReportsRole(boolean hasReportsRole) {
		this.hasReportsRole = hasReportsRole;
	}

}
