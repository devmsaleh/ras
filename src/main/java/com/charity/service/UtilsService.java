package com.charity.service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charity.dao.ApplicationConfigRepository;
import com.charity.dao.BranchRepository;
import com.charity.dao.CollectEntryRepository;
import com.charity.dao.CouponRepository;
import com.charity.dao.FinanceEntryRepository;
import com.charity.dao.ReceiptDetailsRepository;
import com.charity.dao.ReceiptRepository;
import com.charity.dto.CouponAmountDTO;
import com.charity.entities.Accountant;
import com.charity.entities.ApplicationConfig;
import com.charity.entities.Branch;
import com.charity.entities.CollectEntry;
import com.charity.entities.CollectEntryReceipt;
import com.charity.entities.Coupon;
import com.charity.entities.FinanceEntry;
import com.charity.entities.FinanceEntryCollectEntry;
import com.charity.entities.Receipt;
import com.charity.entities.ReceiptDetail;
import com.charity.entities.User;

@Service
@Transactional(rollbackFor = Exception.class)
public class UtilsService {

	private static final Logger log = LoggerFactory.getLogger(UtilsService.class);

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private CollectEntryRepository collectEntryRepository;

	@Autowired
	private ReceiptRepository receiptRepository;

	@Autowired
	private ReceiptDetailsRepository receiptDetailsRepository;

	@Autowired
	private FinanceEntryRepository financeEntryRepository;

	@Autowired
	private ApplicationConfigRepository applicationConfigRepository;

	public Branch getCompanyBranchFromCache(Long companyBranchId) {
		List<Branch> list = branchRepository.getAllBranches();
		for (Branch companyBranchLoop : list) {
			if (companyBranchLoop.getId() == companyBranchId) {
				return companyBranchLoop;
			}
		}
		Branch branch = branchRepository.getOne(companyBranchId);
		if (branch == null)
			branch = new Branch();
		return branch;
	}

	public Coupon getCouponFromCache(Long couponId) {
		List<Coupon> list = couponRepository.getAllCoupons();
		for (Coupon coupon : list) {
			if (coupon.getId() == couponId) {
				return coupon;
			}
		}
		Coupon coupon = couponRepository.findById(couponId).orElse(null);
		if (coupon == null)
			coupon = new Coupon();
		return coupon;
	}

	public Coupon getCouponByBarcode(String barcode) {
		if (StringUtils.isBlank(barcode))
			return new Coupon();
		List<Coupon> list = couponRepository.getAllCoupons();
		for (Coupon couponTypeLoop : list) {
			if (couponTypeLoop.getId().toString().equals(barcode)) {
				return couponTypeLoop;
			}
		}
		Coupon couponType = couponRepository.getOne(Long.valueOf(barcode));
		if (couponType == null)
			couponType = new Coupon();
		return couponType;
	}

	public Coupon loadCouponData(Coupon coupon) {
		coupon = couponRepository.getOne(coupon.getId());
		Hibernate.initialize(coupon.getAccount());
		Hibernate.initialize(coupon.getType());
		return coupon;
	}

	public Receipt loadReceiptDetails(Long receiptId) {
		Receipt receipt = receiptRepository.getOne(receiptId);
		if (receipt != null) {
			Hibernate.initialize(receipt.getReceiptDetailsList());
			for (ReceiptDetail receiptDetail : receipt.getReceiptDetailsList()) {
				Hibernate.initialize(receiptDetail.getCoupon());
			}
		}
		return receipt;
	}

	public List<Coupon> findAllCoupons() {
		List<Coupon> couponsList = couponRepository.findAll();
		for (Coupon coupon : couponsList) {
			// Hibernate.initialize(coupon.getImage());
		}
		return couponsList;
	}

	public CollectEntry createCollectEntry(List<Receipt> receiptsList, User loggedInUser, User delegate) {
		CollectEntry collectEntry = new CollectEntry();
		BigDecimal totalAmount = new BigDecimal(0);
		for (Receipt receipt : receiptsList) {
			totalAmount = totalAmount.add(receipt.getTotalAmount());
		}
		collectEntry.setAmount(totalAmount);
		collectEntry.setCreator(loggedInUser);
		collectEntry.setDelegate(delegate);
		collectEntry.setBranch(new Branch(delegate.getBranchId()));
		collectEntryRepository.save(collectEntry);

		for (Receipt receipt : receiptsList) {
			collectEntry.getReceiptsList().add(new CollectEntryReceipt(collectEntry, receipt));
			receipt.setCollected(true);
			receipt.setCollectedDate(new Date());
		}

		collectEntry.setNumberOfReceipts(collectEntry.getReceiptsList().size());
		collectEntryRepository.save(collectEntry);
		receiptRepository.saveAll(receiptsList);

		return collectEntry;
	}

	public FinanceEntry createFinanceEntry(List<CollectEntry> selectedCollectEntriesList, Accountant accountant,
			User loggedInUser) {
		FinanceEntry financeEntry = new FinanceEntry();
		BigDecimal totalAmount = new BigDecimal(0);
		List<CollectEntry> collectEntriesList = new ArrayList<CollectEntry>();
		for (CollectEntry collectEntry : selectedCollectEntriesList) {
			collectEntriesList.add(loadCollectEntryData(collectEntry.getId()));
		}
		for (CollectEntry collectEntry : collectEntriesList) {
			totalAmount = totalAmount.add(collectEntry.getAmount());
		}
		financeEntry.setAmount(totalAmount);
		financeEntry.setCreator(loggedInUser);
		financeEntry.setAccountant(accountant);
		financeEntryRepository.save(financeEntry);

		for (CollectEntry collectEntry : collectEntriesList) {
			financeEntry.getCollectEntryList().add(new FinanceEntryCollectEntry(financeEntry, collectEntry));
			collectEntry.setCollectedByFinance(true);
			collectEntry.setDateCollectedByFinance(new Date());
			Hibernate.initialize(collectEntry.getReceiptsList());
			for (CollectEntryReceipt collectEntryReceipt : collectEntry.getReceiptsList()) {
				Hibernate.initialize(collectEntryReceipt.getReceipt().getReceiptDetailsList());
				financeEntry.getReceiptDetailsList().addAll(collectEntryReceipt.getReceipt().getReceiptDetailsList());
			}
		}

		financeEntryRepository.save(financeEntry);
		collectEntryRepository.saveAll(collectEntriesList);

		for (ReceiptDetail receiptDetail : financeEntry.getReceiptDetailsList()) {
			Coupon coupon = getCouponFromCache(receiptDetail.getCouponId());
			receiptDetail.setCouponName(coupon.getName());
			receiptDetail.setCouponId(coupon.getId());
		}

		return financeEntry;
	}

	public List<Receipt> findLatestReceipts() {
		List<Receipt> resultList = receiptRepository.findByCollectedFalseOrderByIdDesc();
		for (Receipt receipt : resultList) {
			Hibernate.initialize(receipt.getDelegate());
			Hibernate.initialize(receipt.getReceiptDetailsList());
			for (ReceiptDetail receiptDetail : receipt.getReceiptDetailsList()) {
				Coupon coupon = getCouponFromCache(receiptDetail.getCouponId());
				receiptDetail.setCouponName(coupon.getName());
				receiptDetail.setCouponId(coupon.getId());
			}
		}
		return resultList;
	}

	public List<Receipt> findLatestReceiptsByBranchId(Long branchId) {
		List<Receipt> resultList = receiptRepository.findByCollectedFalseAndBranchIdOrderByIdDesc(branchId);
		for (Receipt receipt : resultList) {
			Hibernate.initialize(receipt.getDelegate());
			Hibernate.initialize(receipt.getReceiptDetailsList());
			for (ReceiptDetail receiptDetail : receipt.getReceiptDetailsList()) {
				Coupon coupon = getCouponFromCache(receiptDetail.getCouponId());
				receiptDetail.setCouponName(coupon.getName());
				receiptDetail.setCouponId(coupon.getId());
			}
		}
		return resultList;
	}

	public CollectEntry loadCollectEntryData(Long collectEntryId) {
		CollectEntry collectEntry = collectEntryRepository.getOne(collectEntryId);
		Hibernate.initialize(collectEntry.getDelegate());
		Hibernate.initialize(collectEntry.getReceiptsList());
		for (CollectEntryReceipt collectEntryReceipt : collectEntry.getReceiptsList()) {
			Hibernate.initialize(collectEntryReceipt.getReceipt().getReceiptDetailsList());
			for (ReceiptDetail receiptDetail : collectEntryReceipt.getReceipt().getReceiptDetailsList()) {
				Coupon coupon = getCouponFromCache(receiptDetail.getCouponId());
				collectEntry.getCouponsList().add(new CouponAmountDTO(coupon.getName(), receiptDetail.getAmount()));
			}
		}
		return collectEntry;

	}

	public List<CollectEntry> findAllNotDepositedCollectEntries() {
		List<CollectEntry> collectEntriesList = collectEntryRepository.findByCollectedByFinanceFalse();
		for (CollectEntry collectEntry : collectEntriesList) {
			Hibernate.initialize(collectEntry.getDelegate());
		}
		return collectEntriesList;
	}

	public List<CollectEntry> findNotDepositedCollectEntriesByBranchId(Long branchId) {
		List<CollectEntry> collectEntriesList = collectEntryRepository
				.findByCollectedByFinanceFalseAndBranchId(branchId);
		for (CollectEntry collectEntry : collectEntriesList) {
			Hibernate.initialize(collectEntry.getDelegate());
		}
		return collectEntriesList;
	}

	public String constructSMSURL(String mobileNumber, Long receiptId, BigDecimal amount, boolean encodeMessageText)
			throws Exception {
		Optional<ApplicationConfig> result = applicationConfigRepository.findById(1l);
		if (!result.isPresent()) {
			return "";
		}
		ApplicationConfig applicationConfig = result.get();
		if (StringUtils.isBlank(applicationConfig.getSmsMessage())) {
			log.error("####### applicationConfig.getSmsMessage() is empty #######");
			return "";
		}
		if (StringUtils.isBlank(applicationConfig.getSmsUserName())) {
			log.error("####### applicationConfig.getSmsUserName() is empty #######");
			return "";
		}
		if (StringUtils.isBlank(applicationConfig.getSmsUserPassword())) {
			log.error("####### applicationConfig.getSmsUserPassword() is empty #######");
			return "";
		}

		if (StringUtils.isBlank(applicationConfig.getSmsURL())) {
			log.error("####### applicationConfig.getSmsURL() is empty #######");
			return "";
		}

		String messageText = applicationConfig.getSmsMessage()
				.replace("{amountParam}", amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString())
				.replace("{receiptNumber}", receiptId.toString());
		if (encodeMessageText)
			messageText = URLEncoder.encode(messageText, "UTF-8");
		log.info("######## messageText: " + messageText);
		String finalURL = applicationConfig.getSmsURL().replace("{userNameParam}", applicationConfig.getSmsUserName())
				.replace("{passwordParam}", applicationConfig.getSmsUserPassword())
				.replace("{messageParam}", messageText).replace("{mobileNumberParam}", mobileNumber);
		return finalURL;
	}

	public List<Receipt> findDelegateNotCollectedReceipts(Long delegateId) {
		List<Receipt> receiptsList = receiptRepository.findDelegateNotCollectedReceipts(delegateId);
		for (Receipt receipt : receiptsList) {
			Hibernate.initialize(receipt.getReceiptDetailsList());
			for (ReceiptDetail receiptDetail : receipt.getReceiptDetailsList()) {
				Coupon coupon = getCouponFromCache(receiptDetail.getCouponId());
				receiptDetail.setCouponName(coupon.getName());
				receiptDetail.setCouponId(coupon.getId());
			}
		}
		return receiptsList;
	}

}
