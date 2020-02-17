package com.charity.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.charity.dao.CouponRepository;
import com.charity.dao.ReceiptRepository;
import com.charity.dao.UserRepository;
import com.charity.entities.Branch;
import com.charity.entities.Coupon;
import com.charity.entities.Receipt;
import com.charity.entities.ReceiptDetail;
import com.charity.entities.User;
import com.charity.enums.GenderTypeEnum;
import com.charity.enums.RoleTypeEnum;
import com.charity.enums.TransactionTypeEnum;
import com.charity.enums.UserTypeEnum;
import com.charity.service.UserService;
import com.charity.service.UtilsService;
import com.charity.util.GeneralUtils;

@Component("receiptBean")
@Scope("view")
public class ReceiptBean implements Serializable {

	private static final long serialVersionUID = 5785500018541107857L;

	private static final Logger log = LoggerFactory.getLogger(ReceiptBean.class);

	@Autowired
	private CurrentUserBean currentUserBean;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private UtilsService utilsService;

	@Autowired
	private ReceiptRepository receiptRepository;

	@Autowired
	private UserRepository userRepository;

	private List<Coupon> couponsList = new ArrayList<Coupon>();

	private Receipt receipt = new Receipt();

	private List<User> delegatesList = new ArrayList<User>();

	private User delegate;

	private User benefactor;

	private Date maxDate;

	private String benefactorMobileNumber;

	private String benefactorDisplayName;

	private List<User> benefactorsList = new ArrayList<User>();

	private boolean benefactorMandatory = false;

	@PostConstruct
	public void init() {
		try {
			log.info("######## init ReceiptBean ###########");
			couponsList = couponRepository.findAllActiveCouponsOrderByPriority();
			if (currentUserBean.isAdmin()
					|| currentUserBean.hasRoleForAllBranches(RoleTypeEnum.ROLE_CREATE_MANUAL_RECEIPT)) {
				delegatesList = userService.findAllDelegates(false);
			} else if (currentUserBean.hasRole(RoleTypeEnum.ROLE_CREATE_MANUAL_RECEIPT)) {
				delegatesList = userService.findAllDelegatesInBranch(currentUserBean.getUser().getBranchId(), false);
			}
			maxDate = new Date();
			maxDate = DateUtils.setHours(maxDate, 23);
			maxDate = DateUtils.setMinutes(maxDate, 59);
			maxDate = DateUtils.setSeconds(maxDate, 59);
			maxDate = DateUtils.setMilliseconds(maxDate, 999);
			receipt.setManualReceiptDate(new Date());
			log.info("######## maxDate: " + maxDate);
		} catch (Exception e) {
			log.error("Exception in init ReceiptBean", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public List<User> completeBenefactorByName(String searchText) {
		log.info("######## completeBenefactorByName,searchText: " + searchText);
		this.benefactorDisplayName = searchText;
		return doCompleteBenefactor(searchText);
	}

	public List<User> completeBenefactorByMobile(String searchText) {
		log.info("######## completeBenefactorByMobile,searchText: " + searchText);
		this.benefactorMobileNumber = searchText;
		return doCompleteBenefactor(searchText);
	}

	private List<User> doCompleteBenefactor(String searchText) {
		if (searchText != null)
			searchText = searchText.trim();
		if (StringUtils.isAlpha(searchText) || StringUtils.containsWhitespace(searchText)) {
			log.info("######## completeBenefactor,findByDisplayName #########");
			benefactorsList = userRepository.findByTypeAndEnabledTrueAndDisplayNameContainingOrderByDisplayName(
					UserTypeEnum.BENEFACTOR, searchText);
			for (User user : benefactorsList) {
				user.setDisplayNameAutoComplete(user.getDisplayName() + " - " + user.getMobileNumber());
			}
			return benefactorsList;
		} else if (StringUtils.isNumeric(searchText)) {
			log.info("######## completeBenefactor,findByMobileNumber #########");
			benefactorsList = userRepository.findByTypeAndEnabledTrueAndMobileNumberStartingWithOrderByDisplayName(
					UserTypeEnum.BENEFACTOR, searchText);
			for (User user : benefactorsList) {
				user.setDisplayNameAutoComplete(user.getDisplayName() + " - " + user.getMobileNumber());
			}
			return benefactorsList;
		} else
			return new ArrayList<User>();
	}

	public void onBenefactorMobileSelect(SelectEvent event) {
		try {
			log.info("############ onBenefactorMobileSelect,obj: " + event.getObject());
			User selectedUser = (User) event.getObject();
			selectedUser.setDisplayNameAutoComplete(selectedUser.getMobileNumber());
		} catch (Exception e) {
			log.error("Exception in init onBenefactorMobileSelect", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private boolean isValidReceiptDetail(ReceiptDetail receiptDetail) {

		if (receiptDetail.getAmount() == null || receiptDetail.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			GeneralUtils.addErrorMessage("يجب إدخال قيمة التبرع لكوبون " + receiptDetail.getCoupon().getName(), null);
			return false;
		}

		if (receiptDetail.getCoupon().getMinimumAmount() != null
				&& receiptDetail.getAmount().compareTo(receiptDetail.getCoupon().getMinimumAmount()) < 0) {
			GeneralUtils.addErrorMessage(" قيمة التبرع لكوبون " + receiptDetail.getCoupon().getName()
					+ " يجب أن لا تكون أقل من " + receiptDetail.getCoupon().getMinimumAmount(), null);
			return false;
		}

		return true;
	}

	public void receiptDetailChanged(ReceiptDetail receiptDetail) {
		log.info("########## receiptDetailChanged ###########");
		boolean valid = isValidReceiptDetail(receiptDetail);
		calculateReceiptTotalAmount();
		log.info("########## receiptDetailChanged,valid: " + valid);
	}

	public void addReceiptDetail(Coupon coupon) {
		try {
			log.info("######## addReceiptDetail,benefactor: " + benefactor + ",amount: " + coupon.getValue()
					+ ",coupon amount: " + coupon.getValue() + ",delegate: " + delegate);

			if (delegate == null) {
				GeneralUtils.addErrorMessage("يرجى اختيار المندوب", null);
				return;
			}

			if (benefactor == null) {
				benefactor = userService.createNewBenefactor(benefactorDisplayName, benefactorMobileNumber,
						GenderTypeEnum.MALE, null, delegate, currentUserBean.getUser());
				if (benefactor != null) {
					log.info("########### NEW BENEFACTOR CREATED FROM addReceiptDetail,ID: " + benefactor.getId());
					benefactorsList.add(benefactor);
				}
			}

			ReceiptDetail receiptDetail = new ReceiptDetail();
			// receiptDetail.setAmount(coupon.getValue());
			receiptDetail.setCoupon(coupon);
			receiptDetail.setCreatedBy(delegate);
			if (benefactor != null)
				receiptDetail.setBenefactor(benefactor);
			receiptDetail.setTransactionType(TransactionTypeEnum.COUPON.getValue());
			receiptDetail.setName(coupon.getName());
			receiptDetail.setReceipt(receipt);
			receipt.getReceiptDetailsList().add(receiptDetail);
			calculateReceiptTotalAmount();
			if (receipt.getReceiptDetailsList().size() == 1) {
				PrimeFaces.current().scrollTo("form:receiptDetailsTableContainer");
			}
		} catch (Exception e) {
			log.error("Exception in init addReceiptDetail", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private void calculateReceiptTotalAmount() {
		BigDecimal totalAmountForReceipt = new BigDecimal(0);
		for (ReceiptDetail receiptDetail : receipt.getReceiptDetailsList()) {
			if (receiptDetail.getAmount() != null)
				totalAmountForReceipt = totalAmountForReceipt.add(receiptDetail.getAmount());
		}
		receipt.setTotalAmount(totalAmountForReceipt);
		receipt.setTotalPaid(totalAmountForReceipt);
	}

	public void removeReceiptDetail(int index) {
		try {
			receipt.getReceiptDetailsList().remove(index);
			calculateReceiptTotalAmount();
			PrimeFaces.current().scrollTo("form:receiptDetailsTableContainer");
		} catch (Exception e) {
			log.error("Exception in init removeReceiptDetail", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void createReceipt() {
		try {
			log.info("######## createReceipt,details: " + receipt.getReceiptDetailsList().size()
					+ ",benefactorDisplayName: " + benefactorDisplayName + ",benefactorMobileNumber: "
					+ benefactorMobileNumber);

			if (receipt.getReceiptDetailsList().size() == 0) {
				GeneralUtils.addErrorMessage("يرجى ادخال الكوبونات", null);
				return;
			}

			for (ReceiptDetail receiptDetail : receipt.getReceiptDetailsList()) {
				if (!isValidReceiptDetail(receiptDetail)) {
					return;
				}
			}

			if (benefactorMandatory && benefactor == null) {
				if (StringUtils.isBlank(benefactorDisplayName)) {
					GeneralUtils.addErrorMessage("يرجى ادخال اسم المتبرع", null);
					return;
				} else if (StringUtils.isBlank(benefactorMobileNumber)) {
					GeneralUtils.addErrorMessage("يرجى ادخال رقم جوال المتبرع", null);
					return;
				}
				benefactor = userService.createNewBenefactor(benefactorDisplayName, benefactorMobileNumber,
						GenderTypeEnum.MALE, null, delegate, currentUserBean.getUser());
				benefactorsList.add(benefactor);
				log.info("########### NEW BENEFACTOR CREATED FROM createReceipt,ID: " + benefactor.getId());
			}

			receipt.setManualReceiptDate(setTimeToNow(receipt.getReceiptDate()));
			receipt.setCreatedBy(currentUserBean.getUser());
			receipt.setBranch(new Branch(delegate.getBranchId()));
			if (benefactor != null) {
				receipt.setDonatorPhoneNumber(benefactor.getMobileNumber());
				receipt.setDonatorName(benefactor.getDisplayName());
			}
			receipt.setDelegate(delegate);
			receipt.setRegion(delegate.getRegion());
			receipt.setLocation(delegate.getLocation());
			calculateReceiptTotalAmount();
			receiptRepository.save(receipt);
			GeneralUtils.showDialogInfo("تم انشاء السند بنجاح" + "<BR/>" + "رقم السند : " + receipt.getId());
			receipt = new Receipt();
			receipt.setManualReceiptDate(new Date());
			delegate = null;
			benefactor = null;
			benefactorDisplayName = null;
			benefactorMobileNumber = null;
			PrimeFaces.current().focus("form:receiptId");
		} catch (Exception e) {
			log.error("Exception in init createReceipt", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private Date setTimeToNow(Date date) {
		if (date == null)
			return null;
		Calendar calNow = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, calNow.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, calNow.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, calNow.get(Calendar.SECOND));
		return cal.getTime();
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public List<Coupon> getCouponsList() {
		return couponsList;
	}

	public void setCouponsList(List<Coupon> couponsList) {
		this.couponsList = couponsList;
	}

	public List<User> getDelegatesList() {
		return delegatesList;
	}

	public void setDelegatesList(List<User> delegatesList) {
		this.delegatesList = delegatesList;
	}

	public User getDelegate() {
		return delegate;
	}

	public void setDelegate(User delegate) {
		this.delegate = delegate;
	}

	public User getBenefactor() {
		return benefactor;
	}

	public void setBenefactor(User benefactor) {
		this.benefactor = benefactor;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public String getBenefactorMobileNumber() {
		return benefactorMobileNumber;
	}

	public void setBenefactorMobileNumber(String benefactorMobileNumber) {
		this.benefactorMobileNumber = benefactorMobileNumber;
	}

	public String getBenefactorDisplayName() {
		return benefactorDisplayName;
	}

	public void setBenefactorDisplayName(String benefactorDisplayName) {
		this.benefactorDisplayName = benefactorDisplayName;
	}

	public List<User> getBenefactorsList() {
		return benefactorsList;
	}

	public void setBenefactorsList(List<User> benefactorsList) {
		this.benefactorsList = benefactorsList;
	}

	public boolean isBenefactorMandatory() {
		return benefactorMandatory;
	}

	public void setBenefactorMandatory(boolean benefactorMandatory) {
		this.benefactorMandatory = benefactorMandatory;
	}

}
