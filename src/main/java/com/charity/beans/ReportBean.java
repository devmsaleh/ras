package com.charity.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.charity.dao.CouponRepository;
import com.charity.dao.ReceiptDetailsRepository;
import com.charity.dao.UserRepository;
import com.charity.dao.UtilsRepository;
import com.charity.dto.DelegateIncomeReportDTO;
import com.charity.entities.Coupon;
import com.charity.entities.User;
import com.charity.service.UserService;
import com.charity.util.Encryptor;
import com.charity.util.GeneralUtils;

@Component("reportBean")
@Scope("view")
public class ReportBean implements Serializable {

	private static final long serialVersionUID = 7643963320294013796L;

	private static final Logger log = LoggerFactory.getLogger(ReportBean.class);

	@Autowired
	private CurrentUserBean currentUserBean;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private UtilsRepository utilsRepository;

	@Autowired
	private ReceiptDetailsRepository receiptDetailsRepository;

	private Date fromDate;

	private Date toDate = new Date();

	private Date todayDate = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);

	private List<DelegateIncomeReportDTO> delegatesTotalList = new ArrayList<DelegateIncomeReportDTO>();

	private List<User> delegatesList = new ArrayList<User>();

	private List<Coupon> couponsList = new ArrayList<Coupon>();

	private String nowDate = "";

	private User delegate;

	private Boolean collected;

	private Coupon coupon;

	private BigDecimal totalAmount = new BigDecimal(0);

	private List<User> benefactorsList = new ArrayList<User>();

	private List<User> filteredBenefactorsList;

	@PostConstruct
	public void init() {
		try {
			log.info("######## init ReportBean,todayDate: " + todayDate);
			fromDate = DateUtils.addDays(new Date(), -30);
			fromDate = DateUtils.truncate(fromDate, Calendar.DAY_OF_MONTH);

			delegatesList = userService.findAllDelegates(false);
			log.info("######## init ReportBean,delegatesList: " + delegatesList.size());

			couponsList = couponRepository.findAllCouponsOrderByPriority();
			log.info("######## init ReportBean,couponsList: " + couponsList.size());

			if (GeneralUtils.getHttpServletRequest().getParameter("id") != null) {
				String id = Encryptor.decrypt(GeneralUtils.getHttpServletRequest().getParameter("id"));
				if (StringUtils.isNotBlank(id)) {
					Optional<User> result = userRepository.findById(Long.parseLong(id));
					if (result.isPresent()) {
						delegate = result.get();
						generateDelegatesIncomeTotalReport();
					}
				}
			}

			log.info("############ uri: " + GeneralUtils.getHttpServletRequest().getRequestURI());

			if (GeneralUtils.getHttpServletRequest().getRequestURI().contains("benefactorsReport.xhtml")) {
				benefactorsList = userService.findAllBenefactors();
				if (benefactorsList != null) {
					log.info("############ benefactorsList: " + benefactorsList.size());
					for (User user : benefactorsList) {
						user.setCoupounsList(receiptDetailsRepository.findBenefactorCoupouns(user.getId()));
					}
				}
			}

		} catch (Exception e) {
			log.error("Exception in init ReportBean", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public String formatDate(Date date) {
		return GeneralUtils.formatDateOnly(date);
	}

	public String formatDateTime(Date date) {
		return GeneralUtils.formatDateTime(date);
	}

	public void generateDelegatesIncomeTotalReport() {
		try {
			log.info("######## generateDelegatesIncomeTotalReport,fromDate: " + fromDate + ",toDate: " + toDate
					+ ",delegate: " + delegate + ",coupon: " + coupon + ",collected: " + collected);
			if (toDate != null) {
				toDate = DateUtils.setHours(toDate, 23);
				toDate = DateUtils.setMinutes(toDate, 59);
				toDate = DateUtils.setSeconds(toDate, 0);
			}
			delegatesTotalList = new ArrayList<DelegateIncomeReportDTO>();
			totalAmount = new BigDecimal(0);
			delegatesTotalList = utilsRepository.generateDelegatesIncomeTotalReport(fromDate, toDate, delegate, coupon,
					collected);
			for (DelegateIncomeReportDTO delegateIncomeReportDTO : delegatesTotalList) {
				totalAmount = totalAmount.add(delegateIncomeReportDTO.getTotalAmount());
			}
		} catch (Exception e) {
			log.error("Exception in init generateDelegatesIncomeTotalReport", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getTodayDate() {
		return todayDate;
	}

	public void setTodayDate(Date todayDate) {
		this.todayDate = todayDate;
	}

	public List<DelegateIncomeReportDTO> getDelegatesTotalList() {
		return delegatesTotalList;
	}

	public void setDelegatesTotalList(List<DelegateIncomeReportDTO> delegatesTotalList) {
		this.delegatesTotalList = delegatesTotalList;
	}

	public String getNowDate() {
		nowDate = GeneralUtils.formatDateTime(new Date());
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public User getDelegate() {
		return delegate;
	}

	public void setDelegate(User delegate) {
		this.delegate = delegate;
	}

	public Boolean getCollected() {
		return collected;
	}

	public void setCollected(Boolean collected) {
		this.collected = collected;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public List<User> getDelegatesList() {
		return delegatesList;
	}

	public void setDelegatesList(List<User> delegatesList) {
		this.delegatesList = delegatesList;
	}

	public List<Coupon> getCouponsList() {
		return couponsList;
	}

	public void setCouponsList(List<Coupon> couponsList) {
		this.couponsList = couponsList;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<User> getBenefactorsList() {
		return benefactorsList;
	}

	public void setBenefactorsList(List<User> benefactorsList) {
		this.benefactorsList = benefactorsList;
	}

	public List<User> getFilteredBenefactorsList() {
		return filteredBenefactorsList;
	}

	public void setFilteredBenefactorsList(List<User> filteredBenefactorsList) {
		this.filteredBenefactorsList = filteredBenefactorsList;
	}
}
