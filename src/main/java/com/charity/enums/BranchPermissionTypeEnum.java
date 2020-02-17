package com.charity.enums;

public enum BranchPermissionTypeEnum {

	ALL_BRANCHES("ALL_BRANCHES_LABEL"), ONE_BRANCH("ONE_BRANCH_LABEL"), MULTIPLE_BRANCHES("MULTIPLE_BRANCHES_LABEL");

	private String label;

	public String getLabel() {
		return label;
	}

	private BranchPermissionTypeEnum(String label) {
		this.label = label;
	}

}
