package com.charity.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.charity.enums.AttachmentEntityEnum;
import com.charity.enums.AttachmentTypeEnum;

@Entity
public class Attachment extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -4917432936811120874L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	private String originalFileName;
	private String fileName;
	private String mimeType;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();
	private String fullPath;
	@Enumerated(EnumType.STRING)
	private AttachmentTypeEnum typeEnum;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AttachmentEntityEnum entityEnum;
	@Column(nullable = false)
	private Long relatedToId;
	private long contentSize;
	@ManyToOne(fetch = FetchType.LAZY)
	private User createdBy;

	@Lob
	private byte[] data;

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public Long getRelatedToId() {
		return relatedToId;
	}

	public void setRelatedToId(Long relatedToId) {
		this.relatedToId = relatedToId;
	}

	public long getContentSize() {
		return contentSize;
	}

	public void setContentSize(long contentSize) {
		this.contentSize = contentSize;
	}

	public AttachmentTypeEnum getTypeEnum() {
		return typeEnum;
	}

	public void setTypeEnum(AttachmentTypeEnum typeEnum) {
		this.typeEnum = typeEnum;
	}

	public AttachmentEntityEnum getEntityEnum() {
		return entityEnum;
	}

	public void setEntityEnum(AttachmentEntityEnum entityEnum) {
		this.entityEnum = entityEnum;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
