package com.charity.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class UserDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -4535226011070233249L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	private String identityNumber;

	private String iqamaNumber;

	private String passportNumber;

	@Temporal(TemporalType.DATE)
	private Date passportExpiryDate;

	@Temporal(TemporalType.DATE)
	private Date iqamahExpiryDate;

	private String sponsorName;

	private String bankName;

	private String accountNumber;

	private String iban;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getIqamaNumber() {
		return iqamaNumber;
	}

	public void setIqamaNumber(String iqamaNumber) {
		this.iqamaNumber = iqamaNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Date getPassportExpiryDate() {
		return passportExpiryDate;
	}

	public void setPassportExpiryDate(Date passportExpiryDate) {
		this.passportExpiryDate = passportExpiryDate;
	}

	public Date getIqamahExpiryDate() {
		return iqamahExpiryDate;
	}

	public void setIqamahExpiryDate(Date iqamahExpiryDate) {
		this.iqamahExpiryDate = iqamahExpiryDate;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

}
