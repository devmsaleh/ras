package com.charity.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.charity.dao.AccountRepository;
import com.charity.dao.CouponRepository;
import com.charity.entities.Account;
import com.charity.entities.Coupon;
import com.charity.entities.CouponType;
import com.charity.enums.RoleTypeEnum;
import com.charity.service.CacheService;
import com.charity.service.UtilsService;
import com.charity.util.GeneralUtils;

@Component("couponBean")
@Scope("view")
public class CouponBean implements Serializable {

	private static final long serialVersionUID = -1902193953099715588L;

	private static final Logger log = LoggerFactory.getLogger(CouponBean.class);

	@Autowired
	private CurrentUserBean currentUserBean;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private UtilsService utilsService;

	private List<Account> accountsList = new ArrayList<Account>();

	private List<CouponType> couponTypesList = new ArrayList<CouponType>();

	private List<Coupon> couponsList = new ArrayList<Coupon>();

	private List<Coupon> filteredCouponsList = new ArrayList<Coupon>();

	private boolean editMode;

	private boolean renderForm;

	private Coupon coupon = new Coupon();

	private Date minDate = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);

	private UploadedFile file;

	@Autowired
	private CacheService cacheService;

	@PostConstruct
	public void init() {
		try {
			log.info("######## init CouponBean ###########");
			if (currentUserBean.isAdmin() || currentUserBean.hasRole(RoleTypeEnum.ROLE_MANAGE_COUPONS)) {
				coupon.setCreator(currentUserBean.getUser());
				accountsList = accountRepository.findAll();
				couponsList = couponRepository.findAllCouponsOrderByPriority();
				filteredCouponsList.addAll(couponsList);
			}
		} catch (Exception e) {
			log.error("Exception in init CouponBean", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public void hideForm() {
		renderForm = false;
	}

	public void setSelectedRow(Coupon coupon) {
		log.info("######## setSelectedRow,coupon: " + coupon.getId());
		coupon = utilsService.loadCouponData(coupon);
		editMode = true;
		renderForm = true;
		this.coupon = coupon;
		if (coupon.getExpiryDate() != null && coupon.getExpiryDate().before(new Date())) {
			this.minDate = coupon.getExpiryDate();
		}

		PrimeFaces.current().scrollTo("form:addNewButton");
	}

	public void addNewButtonClicked() {
		editMode = false;
		renderForm = true;
		coupon = new Coupon();
		minDate = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);
		coupon.setCreator(currentUserBean.getUser());
		PrimeFaces.current().scrollTo("form:addNewButton");
		PrimeFaces.current().focus("form:couponName");
	}

	public void handleFileUpload(FileUploadEvent event) {
		log.info("######## FILE: " + event.getFile().getFileName() + " WAS UPLOADED #########");
		this.file = event.getFile();
	}

	public void createOrUpdateCoupon() {

		if (!editMode) {
			createNewCoupon();
		} else {
			updateCoupon();
		}

		refreshCache(false);

		couponsList.clear();
		filteredCouponsList.clear();
		couponsList = couponRepository.findAllCouponsOrderByPriority();
		filteredCouponsList.addAll(couponsList);

		coupon = new Coupon();
		coupon.setCreator(currentUserBean.getUser());
		renderForm = false;
		file = null;
	}

	private void createNewCoupon() {
		try {
			log.info("######## createNewCoupon,coupon: " + coupon);
			if (file != null) {
				coupon.setImage(file.getContents());
			}
			couponRepository.save(coupon);
			couponsList.add(coupon);
			filteredCouponsList.add(coupon);
			GeneralUtils.showDialogInfo("تم انشاء الكوبون بنجاح");
		} catch (Exception e) {
			log.error("Exception in createNewCoupon", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public void refreshCache(boolean showSuccessMessage) {
		try {
			log.info("######## refreshCache #############");
			cacheService.refreshAllCaches();
			if (showSuccessMessage)
				GeneralUtils.showDialogInfo("تم تحديث الكاش بنجاح");
		} catch (Exception e) {
			log.error("Exception in createNewCoupon", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private void updateCoupon() {
		try {
			log.info("######## updateCoupon,coupon: " + coupon);
			if (file != null) {
				log.info("######## UPDATING COUPON IMAGE ##############");
				coupon.setImage(file.getContents());
			}
			coupon.setLastModifier(currentUserBean.getUser());
			coupon.setDateLastModified(new Date());
			couponRepository.save(coupon);
			GeneralUtils.showDialogInfo("تم تحديث الكوبون بنجاح");
		} catch (Exception e) {
			log.error("Exception in updateCoupon", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public void removeImage() {
		this.file = null;
	}

	public CurrentUserBean getCurrentUserBean() {
		return currentUserBean;
	}

	public void setCurrentUserBean(CurrentUserBean currentUserBean) {
		this.currentUserBean = currentUserBean;
	}

	public AccountRepository getAccountRepository() {
		return accountRepository;
	}

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public List<Account> getAccountsList() {
		return accountsList;
	}

	public void setAccountsList(List<Account> accountsList) {
		this.accountsList = accountsList;
	}

	public List<CouponType> getCouponTypesList() {
		return couponTypesList;
	}

	public void setCouponTypesList(List<CouponType> couponTypesList) {
		this.couponTypesList = couponTypesList;
	}

	public List<Coupon> getCouponsList() {
		return couponsList;
	}

	public void setCouponsList(List<Coupon> couponsList) {
		this.couponsList = couponsList;
	}

	public List<Coupon> getFilteredCouponsList() {
		return filteredCouponsList;
	}

	public void setFilteredCouponsList(List<Coupon> filteredCouponsList) {
		this.filteredCouponsList = filteredCouponsList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public boolean isRenderForm() {
		return renderForm;
	}

	public void setRenderForm(boolean renderForm) {
		this.renderForm = renderForm;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
