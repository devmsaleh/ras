package com.charity.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.charity.enums.PaymentTypeEnum;
import com.charity.util.GeneralUtils;

@Entity
public class Receipt extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1501034240031977325L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@Column(name = "RECEIPT_NUMBER")
	private String number;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate = new Date();

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User createdBy;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User delegate;

	@Column(nullable = false)
	private BigDecimal totalAmount;

	private BigDecimal totalPaid;

	private BigDecimal remaining = new BigDecimal(0);

	private String donatorPhoneNumber;

	private String donatorName;

	@Column(columnDefinition = "bit default 0")
	private boolean collected = false; // تم التحصيل من المندوب

	@Temporal(TemporalType.TIMESTAMP)
	private Date collectedDate; // تاريخ التحصيل من المندوب

	@Column(columnDefinition = "bit default 0")
	private boolean collectedByFinance = false; // تم ترحيلها او ايداعها فى المالية

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date receiptDate = new Date();

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Branch branch;

	@ManyToOne(fetch = FetchType.LAZY)
	private City city;

	@ManyToOne(fetch = FetchType.LAZY)
	private Region region;

	@ManyToOne(fetch = FetchType.LAZY)
	private Location location;

	@Column(columnDefinition = "int default 0")
	private int numberOfPrints = 0;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentTypeEnum paymentType = PaymentTypeEnum.CASH;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "receipt", cascade = CascadeType.ALL)
	private List<ReceiptDetail> receiptDetailsList = new ArrayList<ReceiptDetail>();

	private String source = "AUTO RCT";

	private String notes;

	private String manualReceiptNumber;

	@Temporal(TemporalType.DATE)
	private Date manualReceiptDate;

	@Transient
	private String receiptDateFormatted;

	@Transient
	private String receiptTimeFormatted;

	@OneToOne(mappedBy = "receipt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ReceiptPayment receiptPayment;

	@ManyToOne(fetch = FetchType.LAZY)
	private User benefactor;

	public String getManualReceiptNumber() {
		return manualReceiptNumber;
	}

	public void setManualReceiptNumber(String manualReceiptNumber) {
		this.manualReceiptNumber = manualReceiptNumber;
	}

	public Date getManualReceiptDate() {
		return manualReceiptDate;
	}

	public void setManualReceiptDate(Date manualReceiptDate) {
		this.manualReceiptDate = manualReceiptDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public User getDelegate() {
		return delegate;
	}

	public void setDelegate(User delegate) {
		this.delegate = delegate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public BigDecimal getRemaining() {
		return remaining;
	}

	public void setRemaining(BigDecimal remaining) {
		this.remaining = remaining;
	}

	public String getDonatorPhoneNumber() {
		return donatorPhoneNumber;
	}

	public void setDonatorPhoneNumber(String donatorPhoneNumber) {
		this.donatorPhoneNumber = donatorPhoneNumber;
	}

	public String getDonatorName() {
		return donatorName;
	}

	public void setDonatorName(String donatorName) {
		this.donatorName = donatorName;
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public int getNumberOfPrints() {
		return numberOfPrints;
	}

	public void setNumberOfPrints(int numberOfPrints) {
		this.numberOfPrints = numberOfPrints;
	}

	public PaymentTypeEnum getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}

	public List<ReceiptDetail> getReceiptDetailsList() {
		return receiptDetailsList;
	}

	public void setReceiptDetailsList(List<ReceiptDetail> receiptDetailsList) {
		this.receiptDetailsList = receiptDetailsList;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public boolean isCollectedByFinance() {
		return collectedByFinance;
	}

	public void setCollectedByFinance(boolean collectedByFinance) {
		this.collectedByFinance = collectedByFinance;
	}

	public ReceiptPayment getReceiptPayment() {
		return receiptPayment;
	}

	public void setReceiptPayment(ReceiptPayment receiptPayment) {
		this.receiptPayment = receiptPayment;
	}

	public String getReceiptDateFormatted() {
		receiptDateFormatted = GeneralUtils.formatDateOnly(receiptDate);
		return receiptDateFormatted;
	}

	public void setReceiptDateFormatted(String receiptDateFormatted) {
		this.receiptDateFormatted = receiptDateFormatted;
	}

	public String getReceiptTimeFormatted() {
		receiptTimeFormatted = GeneralUtils.formatTimeOnly(receiptDate);
		return receiptTimeFormatted;
	}

	public void setReceiptTimeFormatted(String receiptTimeFormatted) {
		this.receiptTimeFormatted = receiptTimeFormatted;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Date getCollectedDate() {
		return collectedDate;
	}

	public void setCollectedDate(Date collectedDate) {
		this.collectedDate = collectedDate;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public User getBenefactor() {
		return benefactor;
	}

	public void setBenefactor(User benefactor) {
		this.benefactor = benefactor;
	}

}
