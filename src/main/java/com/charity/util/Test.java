package com.charity.util;

import com.charity.enums.RoleCategoryEnum;

public class Test {

	public static void main(String[] args) {
		System.out.println(RoleCategoryEnum.ADMIN);
		System.out.println(RoleCategoryEnum.ADMIN.getLabel());
		System.out.println(RoleCategoryEnum.ADMIN.name());
		System.out.println(RoleCategoryEnum.ADMIN.toString());
	}

}
