package com.charity.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
public class Location extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -8914516497567965324L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@Column(nullable = false)
	private String nameArabic;

	private String nameEnglish;

	private boolean active = true;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateLastModified;

	@ManyToOne(fetch = FetchType.LAZY)
	private User creator;

	@ManyToOne(fetch = FetchType.LAZY)
	private User lastModifier;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Region region;

	public Location() {

	}

	public Location(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameArabic() {
		return nameArabic;
	}

	public void setNameArabic(String nameArabic) {
		this.nameArabic = nameArabic;
	}

	public String getNameEnglish() {
		return nameEnglish;
	}

	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

}
