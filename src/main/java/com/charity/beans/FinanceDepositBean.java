package com.charity.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.charity.dao.AccountantRepository;
import com.charity.dao.CollectEntryRepository;
import com.charity.dto.CollectionReportDTO;
import com.charity.dto.CouponAmountDTO;
import com.charity.entities.Accountant;
import com.charity.entities.CollectEntry;
import com.charity.entities.Coupon;
import com.charity.entities.FinanceEntry;
import com.charity.entities.ReceiptDetail;
import com.charity.enums.RoleTypeEnum;
import com.charity.service.UtilsService;
import com.charity.util.GeneralUtils;

@Component("financeDepositBean")
@Scope("view")
public class FinanceDepositBean implements Serializable {

	private static final long serialVersionUID = -2915787544238181049L;

	private static final Logger log = LoggerFactory.getLogger(FinanceDepositBean.class);

	@Autowired
	private CurrentUserBean currentUserBean;

	@Autowired
	private CollectEntryRepository collectEntryRepository;

	@Autowired
	private UtilsService utilsService;

	@Autowired
	private AccountantRepository accountantRepository;

	private Accountant accountant;

	private List<Accountant> accountantsList = new ArrayList<Accountant>();

	private List<CollectEntry> collectEntriesList = new ArrayList<CollectEntry>();

	private List<CollectEntry> selectedCollectEntriesList = new ArrayList<CollectEntry>();

	private boolean renderResultDiv = false;

	private CollectionReportDTO collectionReportDTO = new CollectionReportDTO();

	private String nowDate = GeneralUtils.formatDateTime(new Date());

	private String todayDate = GeneralUtils.formatDateOnly(new Date());

	@PostConstruct
	public void init() {
		try {
			log.info("######## init FinanceDepositBean ###########");
			if (currentUserBean.isAdmin() || currentUserBean.hasRoleForAllBranches(RoleTypeEnum.ROLE_DEPOSIT_FINANCE)) {
				accountantsList = accountantRepository.findAllActiveAccountants();
			} else if (currentUserBean.hasRole(RoleTypeEnum.ROLE_DEPOSIT_FINANCE)) {
				accountantsList = accountantRepository.findAllActiveAccountants();
			}

			log.info("######## init FinanceDepositBean,accountantsList: " + accountantsList.size());

			if (accountantsList.size() > 0) {
				findNotDepositedCollectEntries();
			}

		} catch (Exception e) {
			log.error("Exception in init FinanceDepositBean", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	@Transactional
	private void findNotDepositedCollectEntries() {
		try {
			renderResultDiv = true;

			if (currentUserBean.isAdmin() || currentUserBean.hasRoleForAllBranches(RoleTypeEnum.ROLE_DEPOSIT_FINANCE)) {
				collectEntriesList = utilsService.findAllNotDepositedCollectEntries();
			} else if (currentUserBean.hasRole(RoleTypeEnum.ROLE_DEPOSIT_FINANCE)) {
				collectEntriesList = utilsService
						.findNotDepositedCollectEntriesByBranchId(currentUserBean.getUser().getBranchId());
			}

			log.info("######## findNotDepositedCollectEntries" + ",collectEntriesList: " + collectEntriesList.size());

		} catch (Exception e) {
			log.error("Exception in init findNotDepositedCollectEntries", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public void onRowToggle(ToggleEvent event) {
		log.info("############ onRowToggle ##########");
		if (event.getVisibility() == Visibility.VISIBLE) {
			CollectEntry collectEntry = (CollectEntry) event.getData();
			collectEntry = utilsService.loadCollectEntryData(collectEntry.getId());
			log.info("############ onRowToggle,collectEntry.getCouponsList(): " + collectEntry.getCouponsList().size()
					+ ",collectEntriesList: " + collectEntriesList.size());
			int index = collectEntriesList.indexOf(collectEntry);
			if (index >= 0)
				collectEntriesList.set(index, collectEntry);
			else {
				log.info("########### index is zero,collectEntry selected: " + collectEntry.getId());
				for (CollectEntry collectEntryLoop : collectEntriesList) {
					log.info("########### collectEntryLoop: " + collectEntryLoop.getId());
				}
			}
		}
	}

	// ترحيل الايصالات المحصلة للمالية
	public void deposit() {
		try {
			if (selectedCollectEntriesList == null) {
				selectedCollectEntriesList = new ArrayList<CollectEntry>();
			}
			log.info("######## deposit,selectedCollectEntriesList: " + selectedCollectEntriesList.size());
			if (selectedCollectEntriesList.size() == 0) {
				GeneralUtils.showDialogError("يرجى اختيار سندات التحصيل");
				return;
			}
			FinanceEntry financeEntry = utilsService.createFinanceEntry(selectedCollectEntriesList, accountant,
					currentUserBean.getUser());
			log.info("######## deposit,financeEntry.getReceiptDetailsList(): " + financeEntry.getReceiptDetailsList());
			generateCollectionReportDTO(financeEntry);
			renderResultDiv = false;
			collectEntriesList = new ArrayList<CollectEntry>();
			selectedCollectEntriesList = new ArrayList<CollectEntry>();
			findNotDepositedCollectEntries();
			GeneralUtils.showDialogInfo(
					"تم انشاء سند استلام المالية بنجاح" + "<BR/>" + "رقم السند : " + financeEntry.getId());
			nowDate = GeneralUtils.formatDateTime(new Date());
		} catch (Exception e) {
			log.error("Exception in init collect", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private void generateCollectionReportDTO(FinanceEntry financeEntry) {
		collectionReportDTO = new CollectionReportDTO(financeEntry);

		for (ReceiptDetail receiptDetail : financeEntry.getReceiptDetailsList()) {
			log.info("########## receiptDetail.getCouponName(): " + receiptDetail.getCouponName());
			log.info("########## receiptDetail.getCouponId(): " + receiptDetail.getCouponId());
			log.info("########## receiptDetail.getAmount(): " + receiptDetail.getAmount());
			Coupon coupon = new Coupon(receiptDetail.getCouponId(), receiptDetail.getCouponName());
			CouponAmountDTO couponAmountDTO = new CouponAmountDTO(coupon, receiptDetail.getAmount());
			if (!collectionReportDTO.getCouponsList().contains(couponAmountDTO)) {
				log.info("########## NEW COUPON #########");
				collectionReportDTO.getCouponsList().add(couponAmountDTO);
			} else {
				log.info("########## EXISTING COUPON #########");
				CouponAmountDTO couponAmountOld = collectionReportDTO.getCouponsList()
						.get(collectionReportDTO.getCouponsList().indexOf(couponAmountDTO));
				log.info("########## EXISTING COUPON,couponAmountOld: " + couponAmountOld);
				couponAmountOld.setAmount(couponAmountOld.getAmount().add(receiptDetail.getAmount()));
			}

			log.info("########## collectionReportDTO.getCouponsList(): " + collectionReportDTO.getCouponsList().size());

		}
	}

	public Accountant getAccountant() {
		return accountant;
	}

	public void setAccountant(Accountant accountant) {
		this.accountant = accountant;
	}

	public List<Accountant> getAccountantsList() {
		return accountantsList;
	}

	public void setAccountantsList(List<Accountant> accountantsList) {
		this.accountantsList = accountantsList;
	}

	public List<CollectEntry> getCollectEntriesList() {
		return collectEntriesList;
	}

	public void setCollectEntriesList(List<CollectEntry> collectEntriesList) {
		this.collectEntriesList = collectEntriesList;
	}

	public List<CollectEntry> getSelectedCollectEntriesList() {
		return selectedCollectEntriesList;
	}

	public void setSelectedCollectEntriesList(List<CollectEntry> selectedCollectEntriesList) {
		this.selectedCollectEntriesList = selectedCollectEntriesList;
	}

	public boolean isRenderResultDiv() {
		return renderResultDiv;
	}

	public void setRenderResultDiv(boolean renderResultDiv) {
		this.renderResultDiv = renderResultDiv;
	}

	public CollectionReportDTO getCollectionReportDTO() {
		return collectionReportDTO;
	}

	public void setCollectionReportDTO(CollectionReportDTO collectionReportDTO) {
		this.collectionReportDTO = collectionReportDTO;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public String getTodayDate() {
		return todayDate;
	}

	public void setTodayDate(String todayDate) {
		this.todayDate = todayDate;
	}

}
