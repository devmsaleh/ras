package com.charity.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Coupon extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7760800832433579257L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	private String name;

	private boolean active = true;

	@Temporal(TemporalType.DATE)
	private Date expiryDate;

	private boolean mustEnterDonator;

	private BigDecimal value;

	private BigDecimal minimumAmount;

	private String qrCode;

	private int priority;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateLastModified;

	@ManyToOne(fetch = FetchType.LAZY)
	private User creator;

	@ManyToOne(fetch = FetchType.LAZY)
	private User lastModifier;

	@ManyToOne(fetch = FetchType.LAZY)
	private CouponType type;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	@Transient
	private BigDecimal donationAmount;

	public Coupon() {

	}

	public Coupon(Long id) {
		this.id = id;
	}

	public Coupon(Long id, String name) {
		this.id = id;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isMustEnterDonator() {
		return mustEnterDonator;
	}

	public void setMustEnterDonator(boolean mustEnterDonator) {
		this.mustEnterDonator = mustEnterDonator;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(BigDecimal minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateLastModified() {
		return dateLastModified;
	}

	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(User lastModifier) {
		this.lastModifier = lastModifier;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BigDecimal getDonationAmount() {
		return donationAmount;
	}

	public void setDonationAmount(BigDecimal donationAmount) {
		this.donationAmount = donationAmount;
	}

}
