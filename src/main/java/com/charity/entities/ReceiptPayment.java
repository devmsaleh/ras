package com.charity.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.charity.enums.PaymentTypeEnum;

@Entity
public class ReceiptPayment extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7808602708344351995L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	private String creditCardTransactionNumber;

	@OneToOne
	private Receipt receipt;

	private String chequeNumber;

	private Date chequeDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate = new Date();

	@OneToOne
	private User createdBy;

	private BigDecimal cashValue;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentTypeEnum paymentType = PaymentTypeEnum.CASH;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreditCardTransactionNumber() {
		return creditCardTransactionNumber;
	}

	public void setCreditCardTransactionNumber(String creditCardTransactionNumber) {
		this.creditCardTransactionNumber = creditCardTransactionNumber;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
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

	public BigDecimal getCashValue() {
		return cashValue;
	}

	public void setCashValue(BigDecimal cashValue) {
		this.cashValue = cashValue;
	}

	public PaymentTypeEnum getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}

}
