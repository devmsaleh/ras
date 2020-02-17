package com.charity.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.charity.dao.AccountantRepository;
import com.charity.entities.Accountant;
import com.charity.enums.RoleTypeEnum;
import com.charity.util.GeneralUtils;

@Component("accountantManagementBean")
@Scope("view")
public class AccountantManagementBean implements Serializable {

	private static final long serialVersionUID = -3170924523748763728L;

	private static final Logger log = LoggerFactory.getLogger(AccountantManagementBean.class);

	@Autowired
	private CurrentUserBean currentUserBean;

	@Autowired
	private AccountantRepository accountantRepository;

	private Accountant accountant = new Accountant();

	private boolean renderForm;

	private boolean editMode;

	private List<Accountant> accountantList = new ArrayList<Accountant>();

	private List<Accountant> filteredAccountantList = new ArrayList<Accountant>();

	@PostConstruct
	public void init() {
		try {
			log.info("######## init AccountantManagementBean #############");
			if (currentUserBean.isAdmin() || currentUserBean.hasRole(RoleTypeEnum.ROLE_MANAGE_SETTINGS)) {
				accountantList = accountantRepository.findAllAccountants();
				filteredAccountantList.addAll(accountantList);
				log.info("######### accountantList: " + accountantList.size());
			}
		} catch (Exception e) {
			log.error("Exception in init LookupBean", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public String formatDateTime(Date date) {
		return GeneralUtils.formatDateTime(date);
	}

	public void hideForm() {
		renderForm = false;
	}

	public void setSelectedRow(Accountant accountant) {
		log.info("######## setSelectedRow,accountant: " + accountant.getId());
		editMode = true;
		renderForm = true;
		this.accountant = accountant;
		PrimeFaces.current().scrollTo("form:addNewButton");
		PrimeFaces.current().focus("form:accountantName");
	}

	public void addNewButtonClicked() {
		editMode = false;
		renderForm = true;
		accountant = new Accountant();
		accountant.setCreator(currentUserBean.getUser());
		PrimeFaces.current().scrollTo("form:addNewButton");
		PrimeFaces.current().focus("form:accountantName");
	}

	public void createOrUpdateAccountant() {

		if (!editMode) {
			createNewAccountant();
		} else {
			updateAccountant();
		}

		accountant = new Accountant();
		accountant.setCreator(currentUserBean.getUser());
		renderForm = false;
		editMode = false;
	}

	private void createNewAccountant() {
		try {
			log.info("######## createNewAccountant,accountant: " + accountant);
			accountant.setLastModifier(currentUserBean.getUser());
			accountant.setDateLastModified(new Date());
			accountantRepository.save(accountant);
			accountantList.add(accountant);
			filteredAccountantList.add(accountant);
			GeneralUtils.showDialogInfo("تم إضافة مسؤول الحسابات بنجاح");
		} catch (Exception e) {
			log.error("Exception in createNewAccountant", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private void updateAccountant() {
		try {
			log.info("######## updateAccountant,accountant: " + accountant);
			accountant.setLastModifier(currentUserBean.getUser());
			accountant.setDateLastModified(new Date());
			accountantRepository.save(accountant);
			GeneralUtils.showDialogInfo("تم تحديث مسؤول الحسابات بنجاح");
		} catch (Exception e) {
			log.error("Exception in updateAccountant", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public Accountant getAccountant() {
		return accountant;
	}

	public void setAccountant(Accountant accountant) {
		this.accountant = accountant;
	}

	public boolean isRenderForm() {
		return renderForm;
	}

	public void setRenderForm(boolean renderForm) {
		this.renderForm = renderForm;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<Accountant> getAccountantList() {
		return accountantList;
	}

	public void setAccountantList(List<Accountant> accountantList) {
		this.accountantList = accountantList;
	}

	public List<Accountant> getFilteredAccountantList() {
		return filteredAccountantList;
	}

	public void setFilteredAccountantList(List<Accountant> filteredAccountantList) {
		this.filteredAccountantList = filteredAccountantList;
	}

}
