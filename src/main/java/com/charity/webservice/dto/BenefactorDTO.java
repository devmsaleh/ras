package com.charity.webservice.dto;

import com.charity.entities.User;
import com.charity.enums.GenderTypeEnum;

public class BenefactorDTO {

	private Long id;
	private String name;
	private String mobileNumber;
	private GenderTypeEnum gender = GenderTypeEnum.MALE;
	private int age;

	public BenefactorDTO() {

	}

	public BenefactorDTO(User user) {
		this.id = user.getId();
		this.name = user.getDisplayName();
		this.mobileNumber = user.getMobileNumber();
		this.gender = user.getGenderType();
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public GenderTypeEnum getGender() {
		return gender;
	}

	public void setGender(GenderTypeEnum gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "BenefactorDTO [id=" + id + ", name=" + name + ", mobileNumber=" + mobileNumber + ", gender=" + gender
				+ ", age=" + age + "]";
	}

}
