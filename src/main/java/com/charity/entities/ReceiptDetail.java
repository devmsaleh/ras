package com.charity.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.charity.enums.PaymentTypeEnum;
import com.charity.util.GeneralUtils;

@Entity
public class ReceiptDetail extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4002911698652426751L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate = new Date();

	@Transient
	private String dateCreatedStr;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	private Coupon coupon;

	@Column(name = "coupon_id", insertable = false, updatable = false)
	private Long couponId;

	@Column(nullable = false)
	private BigDecimal amount;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Receipt receipt;

	@Column(name = "RECEIPT_ID", insertable = false, updatable = false)
	private Long receiptId;

	@ManyToOne(fetch = FetchType.LAZY)
	private ProjectStudy projectStudy;

	@ManyToOne(fetch = FetchType.LAZY)
	private Country projectCountry;

	@ManyToOne(fetch = FetchType.LAZY)
	private User benefactor;

	@ManyToOne(fetch = FetchType.LAZY)
	private NewProjectType receiptType;

	private BigDecimal projectCommitment;

	private String projectName;

	private String notes;

	private String donatorName;

	@ManyToOne(fetch = FetchType.LAZY)
	private Country donatorCountry;

	private String donatorMobile;

	private String donatorPhone;

	private String donatorPOBOX;

	private String donatorEmail;

	@ManyToOne(fetch = FetchType.LAZY)
	private OldProject oldProject;

	private String transactionType;

	@ManyToOne(fetch = FetchType.LAZY)
	private Orphan orphan;

	private BigDecimal caseAmount;

	private BigDecimal giftAmount;

	@ManyToOne(fetch = FetchType.LAZY)
	private GiftType giftType;

	private String casePaymentType;

	private String sponsorFor;

	private String name;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentTypeEnum paymentType = PaymentTypeEnum.CASH;

	private BigDecimal caseAmountPerMonth;

	private Integer casePaymentNumberOfMonths = 1;

	@ManyToOne(fetch = FetchType.LAZY)
	private FirstTitle firstTitle;

	@Temporal(TemporalType.DATE)
	private Date sponsorshipStartDate = new Date();

	@Transient
	private String couponName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public ProjectStudy getProjectStudy() {
		return projectStudy;
	}

	public void setProjectStudy(ProjectStudy projectStudy) {
		this.projectStudy = projectStudy;
	}

	public Country getProjectCountry() {
		return projectCountry;
	}

	public void setProjectCountry(Country projectCountry) {
		this.projectCountry = projectCountry;
	}

	public NewProjectType getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(NewProjectType receiptType) {
		this.receiptType = receiptType;
	}

	public BigDecimal getProjectCommitment() {
		return projectCommitment;
	}

	public void setProjectCommitment(BigDecimal projectCommitment) {
		this.projectCommitment = projectCommitment;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDonatorName() {
		return donatorName;
	}

	public void setDonatorName(String donatorName) {
		this.donatorName = donatorName;
	}

	public Country getDonatorCountry() {
		return donatorCountry;
	}

	public void setDonatorCountry(Country donatorCountry) {
		this.donatorCountry = donatorCountry;
	}

	public String getDonatorMobile() {
		return donatorMobile;
	}

	public void setDonatorMobile(String donatorMobile) {
		this.donatorMobile = donatorMobile;
	}

	public String getDonatorPhone() {
		return donatorPhone;
	}

	public void setDonatorPhone(String donatorPhone) {
		this.donatorPhone = donatorPhone;
	}

	public String getDonatorPOBOX() {
		return donatorPOBOX;
	}

	public void setDonatorPOBOX(String donatorPOBOX) {
		this.donatorPOBOX = donatorPOBOX;
	}

	public String getDonatorEmail() {
		return donatorEmail;
	}

	public void setDonatorEmail(String donatorEmail) {
		this.donatorEmail = donatorEmail;
	}

	public OldProject getOldProject() {
		return oldProject;
	}

	public void setOldProject(OldProject oldProject) {
		this.oldProject = oldProject;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Orphan getOrphan() {
		return orphan;
	}

	public void setOrphan(Orphan orphan) {
		this.orphan = orphan;
	}

	public BigDecimal getCaseAmount() {
		return caseAmount;
	}

	public void setCaseAmount(BigDecimal caseAmount) {
		this.caseAmount = caseAmount;
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public GiftType getGiftType() {
		return giftType;
	}

	public void setGiftType(GiftType giftType) {
		this.giftType = giftType;
	}

	public String getCasePaymentType() {
		return casePaymentType;
	}

	public void setCasePaymentType(String casePaymentType) {
		this.casePaymentType = casePaymentType;
	}

	public String getSponsorFor() {
		return sponsorFor;
	}

	public void setSponsorFor(String sponsorFor) {
		this.sponsorFor = sponsorFor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PaymentTypeEnum getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getCaseAmountPerMonth() {
		return caseAmountPerMonth;
	}

	public void setCaseAmountPerMonth(BigDecimal caseAmountPerMonth) {
		this.caseAmountPerMonth = caseAmountPerMonth;
	}

	public Integer getCasePaymentNumberOfMonths() {
		return casePaymentNumberOfMonths;
	}

	public void setCasePaymentNumberOfMonths(Integer casePaymentNumberOfMonths) {
		this.casePaymentNumberOfMonths = casePaymentNumberOfMonths;
	}

	public FirstTitle getFirstTitle() {
		return firstTitle;
	}

	public void setFirstTitle(FirstTitle firstTitle) {
		this.firstTitle = firstTitle;
	}

	public Date getSponsorshipStartDate() {
		return sponsorshipStartDate;
	}

	public void setSponsorshipStartDate(Date sponsorshipStartDate) {
		this.sponsorshipStartDate = sponsorshipStartDate;
	}

	public User getBenefactor() {
		return benefactor;
	}

	public void setBenefactor(User benefactor) {
		this.benefactor = benefactor;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public String getDateCreatedStr() {
		dateCreatedStr = GeneralUtils.formatDateTime(creationDate);
		return dateCreatedStr;
	}

	public void setDateCreatedStr(String dateCreatedStr) {
		this.dateCreatedStr = dateCreatedStr;
	}

}
