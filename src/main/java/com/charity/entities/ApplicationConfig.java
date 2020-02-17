package com.charity.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ApplicationConfig extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -3782242103165789343L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateLastModified;

	@ManyToOne(fetch = FetchType.LAZY)
	private User lastModifier;

	private String smsURL;

	private String smsUserName;

	private String smsUserPassword;

	private String smsMessage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateLastModified() {
		return dateLastModified;
	}

	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	public User getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(User lastModifier) {
		this.lastModifier = lastModifier;
	}

	public String getSmsURL() {
		return smsURL;
	}

	public void setSmsURL(String smsURL) {
		this.smsURL = smsURL;
	}

	public String getSmsUserName() {
		return smsUserName;
	}

	public void setSmsUserName(String smsUserName) {
		this.smsUserName = smsUserName;
	}

	public String getSmsUserPassword() {
		return smsUserPassword;
	}

	public void setSmsUserPassword(String smsUserPassword) {
		this.smsUserPassword = smsUserPassword;
	}

	public String getSmsMessage() {
		return smsMessage;
	}

	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
	}

}
