package com.charity.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Custody extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -782674435000710919L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User creator;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	private User responsibleUser;

	private String deviceType;

	private String deviceNumber;

	private String serialNumber;

	private String simNumber;

	private String cardNumber;

	private String manualReceiptsBookNumber;

	private String manualReceiptsBookNumberStart;

	private String manualReceiptsBookNumberEnd;

	private String electronicReceiptsBookNumber;

	private String electronicReceiptsBookNumberStart;

	private String electronicReceiptsBookNumberEnd;

	private String notes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public User getResponsibleUser() {
		return responsibleUser;
	}

	public void setResponsibleUser(User responsibleUser) {
		this.responsibleUser = responsibleUser;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSimNumber() {
		return simNumber;
	}

	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getManualReceiptsBookNumber() {
		return manualReceiptsBookNumber;
	}

	public void setManualReceiptsBookNumber(String manualReceiptsBookNumber) {
		this.manualReceiptsBookNumber = manualReceiptsBookNumber;
	}

	public String getManualReceiptsBookNumberStart() {
		return manualReceiptsBookNumberStart;
	}

	public void setManualReceiptsBookNumberStart(String manualReceiptsBookNumberStart) {
		this.manualReceiptsBookNumberStart = manualReceiptsBookNumberStart;
	}

	public String getManualReceiptsBookNumberEnd() {
		return manualReceiptsBookNumberEnd;
	}

	public void setManualReceiptsBookNumberEnd(String manualReceiptsBookNumberEnd) {
		this.manualReceiptsBookNumberEnd = manualReceiptsBookNumberEnd;
	}

	public String getElectronicReceiptsBookNumber() {
		return electronicReceiptsBookNumber;
	}

	public void setElectronicReceiptsBookNumber(String electronicReceiptsBookNumber) {
		this.electronicReceiptsBookNumber = electronicReceiptsBookNumber;
	}

	public String getElectronicReceiptsBookNumberStart() {
		return electronicReceiptsBookNumberStart;
	}

	public void setElectronicReceiptsBookNumberStart(String electronicReceiptsBookNumberStart) {
		this.electronicReceiptsBookNumberStart = electronicReceiptsBookNumberStart;
	}

	public String getElectronicReceiptsBookNumberEnd() {
		return electronicReceiptsBookNumberEnd;
	}

	public void setElectronicReceiptsBookNumberEnd(String electronicReceiptsBookNumberEnd) {
		this.electronicReceiptsBookNumberEnd = electronicReceiptsBookNumberEnd;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
