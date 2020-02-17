package com.charity.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.charity.dao.ReceiptRepository;
import com.charity.dao.UserRepository;
import com.charity.dto.CollectionDTO;
import com.charity.dto.CollectionReportDTO;
import com.charity.dto.CouponAmountDTO;
import com.charity.entities.CollectEntry;
import com.charity.entities.Coupon;
import com.charity.entities.Receipt;
import com.charity.entities.ReceiptDetail;
import com.charity.entities.User;
import com.charity.enums.RoleTypeEnum;
import com.charity.service.UserService;
import com.charity.service.UtilsService;
import com.charity.util.Encryptor;
import com.charity.util.GeneralUtils;

@Component("collectionBean")
@Scope("view")
public class CollectionBean implements Serializable {

	private static final long serialVersionUID = -4484312687673859157L;

	private static final Logger log = LoggerFactory.getLogger(CollectionBean.class);

	@Autowired
	private CurrentUserBean currentUserBean;

	@Autowired
	private ReceiptRepository receiptRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private UtilsService utilsService;

	@Autowired
	private UserRepository userRepository;

	private List<User> delegatesList = new ArrayList<User>();

	private User delegate;

	private CollectionDTO collectionDTO = new CollectionDTO();

	private boolean renderResultDiv = false;

	private CollectionReportDTO collectionReportDTO = new CollectionReportDTO();

	private String nowDate = GeneralUtils.formatDateTime(new Date());

	private String todayDate = GeneralUtils.formatDateOnly(new Date());

	private long collectEntryId;

	@PostConstruct
	public void init() {
		try {
			log.info("######## init collectionBean ###########");
			if (currentUserBean.isAdmin() || currentUserBean.hasRoleForAllBranches(RoleTypeEnum.ROLE_COLLECT_MONEY)) {
				delegatesList = userService.findAllDelegates(false);
			} else if (currentUserBean.hasRole(RoleTypeEnum.ROLE_COLLECT_MONEY)) {
				delegatesList = userService.findAllDelegatesInBranch(currentUserBean.getUser().getBranchId(), false);
			}

			if (GeneralUtils.getHttpServletRequest().getParameter("id") != null) {
				String id = Encryptor.decrypt(GeneralUtils.getHttpServletRequest().getParameter("id"));
				if (StringUtils.isNotBlank(id)) {
					Optional<User> result = userRepository.findById(Long.parseLong(id));
					if (result.isPresent()) {
						delegate = result.get();
						findNotCollectedReceipts();
					}
				}
			}

		} catch (Exception e) {
			log.error("Exception in init collectionBean", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public void findNotCollectedReceipts() {
		try {
			if (delegate == null)
				return;
			renderResultDiv = true;
			// find not collected and not collectedByFinance
			log.info("######## findNotCollectedReceipts,delegate: " + delegate.getId());
			List<Receipt> receiptsList = utilsService.findDelegateNotCollectedReceipts(delegate.getId());
			log.info("######## findNotCollectedReceipts,receiptsList: " + receiptsList.size());
			collectionDTO = new CollectionDTO(receiptsList);
		} catch (Exception e) {
			log.error("Exception in init findNotCollectedReceipts", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	// تحصيل
	public void collect() {
		try {
			log.info("######## collect,selected receipts: " + collectionDTO.getReceiptsList().size());
			collectEntryId = 0;
			CollectEntry collectEntry = utilsService.createCollectEntry(collectionDTO.getReceiptsList(),
					currentUserBean.getUser(), delegate);
			collectEntryId = collectEntry.getId();
			generateCollectionReportDTO(collectEntry, collectionDTO.getReceiptsList());
			collectionDTO = new CollectionDTO();
			renderResultDiv = false;
			delegate = null;
			GeneralUtils
					.showDialogInfo("تم انشاء سند الاستلام بنجاح" + "<BR/>" + "رقم السند : " + collectEntry.getId());
			nowDate = GeneralUtils.formatDateTime(new Date());
		} catch (Exception e) {
			log.error("Exception in init collect", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private void generateCollectionReportDTO(CollectEntry collectEntry, List<Receipt> receiptsList) {
		collectionReportDTO = new CollectionReportDTO(collectEntry);
		List<ReceiptDetail> receiptDetailsList = new ArrayList<ReceiptDetail>();
		for (Receipt receipt : receiptsList) {
			receiptDetailsList.addAll(receipt.getReceiptDetailsList());
		}

		for (ReceiptDetail receiptDetail : receiptDetailsList) {
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

	public CollectionDTO getCollectionDTO() {
		return collectionDTO;
	}

	public void setCollectionDTO(CollectionDTO collectionDTO) {
		this.collectionDTO = collectionDTO;
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
		nowDate = GeneralUtils.formatDateTime(new Date());
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

	public long getCollectEntryId() {
		return collectEntryId;
	}

	public void setCollectEntryId(long collectEntryId) {
		this.collectEntryId = collectEntryId;
	}

}
