package com.charity.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.charity.dao.AttachmentRepository;
import com.charity.util.GeneralUtils;

@Component("loadImageBean")
@Scope("session")
public class LoadImageBean implements Serializable {

	private static final long serialVersionUID = 6579819025654911892L;

	private static final Logger log = LoggerFactory.getLogger(LoadImageBean.class);

	@Autowired
	private AttachmentRepository attachmentRepository;

	private String defaultAvatarPath;

	@PostConstruct
	public void init() {
		try {
			defaultAvatarPath = GeneralUtils.getDefaultAvatarPath();
		} catch (Exception e) {
			log.error("Exception in init LoadImageBean", e);
		}
	}

	public StreamedContent getCompanyLogo() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			String companyId = context.getExternalContext().getRequestParameterMap().get("companyId");
			String fullPath = "";
			if (StringUtils.isBlank(fullPath)) {
				fullPath = defaultAvatarPath;
			}
			Path path = Paths.get(fullPath);
			byte[] data = Files.readAllBytes(path);
			return new DefaultStreamedContent(new ByteArrayInputStream(data));
		}
	}

	public String getDefaultAvatarPath() {
		return defaultAvatarPath;
	}

	public void setDefaultAvatarPath(String defaultAvatarPath) {
		this.defaultAvatarPath = defaultAvatarPath;
	}

}
