package com.charity.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.charity.dao.ApplicationConfigRepository;
import com.charity.entities.ApplicationConfig;
import com.charity.service.UtilsService;
import com.charity.util.GeneralUtils;

@Component("applicationConfigBean")
@Scope("view")
public class ApplicationConfigBean implements Serializable {

	private static final long serialVersionUID = 5493943020504964121L;

	private static final Logger log = LoggerFactory.getLogger(ApplicationConfigBean.class);

	@Autowired
	private CurrentUserBean currentUserBean;

	@Autowired
	private ApplicationConfigRepository applicationConfigRepository;

	@Autowired
	private UtilsService utilsService;

	private ApplicationConfig applicationConfig = new ApplicationConfig();

	@PostConstruct
	@Transactional(readOnly = true)
	public void init() {
		try {
			log.info("######## init ApplicationConfigBean ###########");
			Optional<ApplicationConfig> result = applicationConfigRepository.findById(1l);
			if (!result.isPresent()) {
				applicationConfig = new ApplicationConfig();
			} else {
				applicationConfig = result.get();
			}
		} catch (Exception e) {
			log.error("Exception in init ApplicationConfigBean", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public void saveConfig() {
		try {
			log.info("######## saveConfig #############");
			applicationConfig.setDateLastModified(new Date());
			applicationConfig.setLastModifier(currentUserBean.getUser());
			applicationConfigRepository.save(applicationConfig);
			GeneralUtils.showDialogInfo("تم حفظ الإعدادات بنجاح");
		} catch (Exception e) {
			log.error("Exception in saveConfig", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
}
