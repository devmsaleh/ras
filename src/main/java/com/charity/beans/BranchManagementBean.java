package com.charity.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.charity.dao.BranchRepository;
import com.charity.dao.CityRepository;
import com.charity.dao.EmarahRepository;
import com.charity.entities.Branch;
import com.charity.entities.City;
import com.charity.entities.Emarah;
import com.charity.enums.RoleTypeEnum;
import com.charity.util.Constants;
import com.charity.util.GeneralUtils;

@Component("branchManagementBean")
@Scope("view")
public class BranchManagementBean implements Serializable {

	private static final long serialVersionUID = 7793484677582071532L;

	private static final Logger log = LoggerFactory.getLogger(BranchManagementBean.class);

	@Autowired
	private CurrentUserBean currentUserBean;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private EmarahRepository emarahRepository;

	@Autowired
	private CityRepository cityRepository;

	private Branch branch = new Branch();

	private boolean renderForm;

	private boolean editMode;

	private List<Branch> branchList = new ArrayList<Branch>();

	private List<Branch> filteredBranchList = new ArrayList<Branch>();

	private List<Emarah> emarahList = new ArrayList<Emarah>();

	private List<City> cityList = new ArrayList<City>();

	private Emarah selectedEmarah;

	private City selectedCity;

	@PostConstruct
	public void init() {
		try {
			log.info("######## init BranchManagementBean #############");
			if (currentUserBean.isAdmin() || currentUserBean.hasRole(RoleTypeEnum.ROLE_MANAGE_SETTINGS)) {
				emarahList = emarahRepository.findActiveEmarahs();
				branchList = branchRepository.findAllBranches();
				removeAllBranchesBranch();
				filteredBranchList.addAll(branchList);
				log.info("######### branchList: " + branchList.size() + ",emarahList: " + emarahList);
				if (emarahList.size() > 0) {
					selectedEmarah = emarahList.get(0);
					emarahChanged();
				}
			}
		} catch (Exception e) {
			log.error("Exception in init BranchManagementBean", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private void removeAllBranchesBranch() {
		branchList.removeIf(branchLoop -> branchLoop.getId() == Constants.ALL_BRANCHES_ID);
	}

	public void emarahChanged() {
		try {
			log.info("######## emarahChanged,selectedEmarah: " + selectedEmarah);
			cityList.clear();
			selectedCity = null;
			if (selectedEmarah != null) {
				cityList = cityRepository.findByEmarahIdAndActiveTrueOrderByNameArabic(selectedEmarah.getId());
				if (cityList.size() > 0) {
					selectedCity = cityList.get(0);
				}
			}
			log.info("######## cityList: " + cityList.size());
		} catch (Exception e) {
			log.error("Exception in emarahChanged", e);
		}
	}

	public String formatDateTime(Date date) {
		return GeneralUtils.formatDateTime(date);
	}

	public void hideForm() {
		renderForm = false;
	}

	public void setSelectedRow(Branch branch) {
		log.info("######## setSelectedRow,branch: " + branch.getId());
		log.info("######## setSelectedRow,branch.getCity().getEmarahId(): " + branch.getCity().getEmarahId());
		editMode = true;
		renderForm = true;
		this.branch = branch;
		Optional<Emarah> result = emarahRepository.findById(branch.getCity().getEmarahId());
		if (result.isPresent()) {
			selectedEmarah = result.get();
			emarahChanged();
			selectedCity = branch.getCity();
		}
		PrimeFaces.current().scrollTo("form:addNewButton");
		PrimeFaces.current().focus("form:branchName");
	}

	public void addNewButtonClicked() {
		editMode = false;
		renderForm = true;
		branch = new Branch();
		branch.setCreator(currentUserBean.getUser());
		PrimeFaces.current().scrollTo("form:addNewButton");
		PrimeFaces.current().focus("form:branchName");
	}

	public void createOrUpdateBranch() {

		if (selectedCity != null)
			branch.setCity(selectedCity);

		if (!editMode) {
			createNewBranch();
		} else {
			updateBranch();
		}

		branch = new Branch();
		branch.setCreator(currentUserBean.getUser());
		renderForm = false;
		editMode = false;
	}

	private void createNewBranch() {
		try {
			log.info("######## createNewBranch,accountant: " + branch);
			branch.setLastModifier(currentUserBean.getUser());
			branch.setDateLastModified(new Date());
			branchRepository.save(branch);
			branchList.add(branch);
			filteredBranchList.add(branch);
			GeneralUtils.showDialogInfo("تم إضافة فرع الجمعية بنجاح");
		} catch (Exception e) {
			log.error("Exception in createNewBranch", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private void updateBranch() {
		try {
			log.info("######## updateBranch,accountant: " + branch);
			branch.setLastModifier(currentUserBean.getUser());
			branch.setDateLastModified(new Date());
			branchRepository.save(branch);
			GeneralUtils.showDialogInfo("تم تحديث فرع الجمعية بنجاح");
		} catch (Exception e) {
			log.error("Exception in updateBranch", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
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

	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}

	public List<Branch> getFilteredBranchList() {
		return filteredBranchList;
	}

	public void setFilteredBranchList(List<Branch> filteredBranchList) {
		this.filteredBranchList = filteredBranchList;
	}

	public List<Emarah> getEmarahList() {
		return emarahList;
	}

	public void setEmarahList(List<Emarah> emarahList) {
		this.emarahList = emarahList;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}

	public Emarah getSelectedEmarah() {
		return selectedEmarah;
	}

	public void setSelectedEmarah(Emarah selectedEmarah) {
		this.selectedEmarah = selectedEmarah;
	}

	public City getSelectedCity() {
		return selectedCity;
	}

	public void setSelectedCity(City selectedCity) {
		this.selectedCity = selectedCity;
	}

}
