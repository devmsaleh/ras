package com.charity.webservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charity.dao.BankChequeRepository;
import com.charity.dao.BankTransferRepository;
import com.charity.dao.CouponRepository;
import com.charity.dao.DelegateCouponRepository;
import com.charity.dao.ErrorCodeRepository;
import com.charity.dao.ReceiptDetailsRepository;
import com.charity.dao.ReceiptRepository;
import com.charity.dao.TokenRepository;
import com.charity.dao.UserRepository;
import com.charity.entities.Branch;
import com.charity.entities.City;
import com.charity.entities.Coupon;
import com.charity.entities.Location;
import com.charity.entities.Receipt;
import com.charity.entities.ReceiptDetail;
import com.charity.entities.ReceiptPayment;
import com.charity.entities.Region;
import com.charity.entities.User;
import com.charity.enums.CouponTypeEnum;
import com.charity.enums.ErrorCodeEnum;
import com.charity.enums.PaymentTypeEnum;
import com.charity.enums.TransactionTypeEnum;
import com.charity.service.CacheService;
import com.charity.service.UserService;
import com.charity.service.UtilsService;
import com.charity.util.GeneralUtils;
import com.charity.webservice.dto.BenefactorDTO;
import com.charity.webservice.dto.CouponReportDTO;
import com.charity.webservice.dto.CouponTypeDTO;
import com.charity.webservice.dto.DelegateDTO;
import com.charity.webservice.dto.NewCouponDTO;
import com.charity.webservice.dto.ReceiptDTO;
import com.charity.webservice.dto.ReceiptDetailDTO;
import com.charity.webservice.dto.ReceiptPaymentDTO;
import com.charity.webservice.dto.ReceiptPrintDTO;
import com.charity.webservice.dto.ReceiptsReportDTO;
import com.charity.webservice.dto.ServiceResponse;
import com.charity.webservice.dto.SupervisorReportDTO;

@Service
public class CharityServiceBase {

	private static final Logger logger = LoggerFactory.getLogger(CharityServiceBase.class);

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected UserService userService;

	@Autowired
	protected DelegateCouponRepository delegateCouponRepository;

	@Autowired
	protected TokenRepository tokenRepository;

	@Autowired
	protected ErrorCodeRepository errorCodeRepository;

	@Autowired
	protected CouponRepository couponRepository;

	@Autowired
	protected ReceiptRepository receiptRepository;

	@Autowired
	protected ServiceValidation validation;

	@Autowired
	protected UtilsService utilsService;

	@Autowired
	protected ReceiptDetailsRepository receiptDetailsRepository;

	@Autowired
	protected CacheService cacheService;

	@Autowired
	protected BankChequeRepository bankChequeRepository;

	@Autowired
	protected BankTransferRepository bankTransferRepository;

	protected boolean isSuccess(ErrorCodeEnum errorCode) {
		return errorCode != null && errorCode.intValue() == ErrorCodeEnum.SUCCESS_CODE.intValue();
	}

	protected boolean isError(ErrorCodeEnum errorCode) {
		return errorCode == null || errorCode.intValue() != ErrorCodeEnum.SUCCESS_CODE.intValue();
	}

	protected boolean isSuccess(int errorCode) {
		return errorCode == ErrorCodeEnum.SUCCESS_CODE.intValue();
	}

	protected boolean isError(int errorCode) {
		return errorCode != ErrorCodeEnum.SUCCESS_CODE.intValue();
	}

	protected boolean isResponseHasError(ServiceResponse response) {
		return response == null || response.getErrorCode().intValue() != ErrorCodeEnum.SUCCESS_CODE.intValue();
	}

	public DelegateDTO convertDelegateToDTO(User delegate) {
		DelegateDTO delegateDTO = new DelegateDTO();
		delegateDTO.setId(delegate.getId().toString());
		delegateDTO.setAdmin(delegate.isAdmin());
		delegateDTO.setCharityBox(delegate.isCharityBox());
		delegateDTO.setExpiryDate(GeneralUtils.formatDate(delegate.getExpiryDate()));
		delegateDTO.setMaxLimit(delegate.getMaxLimit());
		delegateDTO.setName(delegate.getDisplayName());
		delegateDTO.setNumber(delegate.getId().toString());
		delegateDTO.setStartDate(GeneralUtils.formatDate(delegate.getStartDate()));
		delegateDTO.setToken(delegate.getToken());
		delegateDTO.setUserName(delegate.getUserName());
		delegateDTO.setShowCollectors(delegate.isShowCollectors());
		return delegateDTO;
	}

	public List<BenefactorDTO> convertBenefactorListToDTO(List<User> benefactorList) {
		List<BenefactorDTO> resultList = new ArrayList<BenefactorDTO>(benefactorList.size());
		for (User user : benefactorList) {
			resultList.add(new BenefactorDTO(user));
		}
		return resultList;
	}

	public Receipt convertReceipDTOToReceipt(ReceiptDTO receiptDTO) {
		Receipt receipt = new Receipt();
		receipt.setCreatedBy(new User(receiptDTO.getDelegateId()));
		receipt.setDelegate(new User(receiptDTO.getDelegateId()));

		receipt.setPaymentType(receiptDTO.getPaymentType());
		User benefactor = null;
		User delegate = userService.findById(receiptDTO.getDelegateId());
		logger.info("########### CREATE RECEIPT,BENEFACTOR: " + receiptDTO.getBenefactor());
		if (!GeneralUtils.isEmptyNumber(receiptDTO.getBenefactor().getId())) {
			logger.info("########### CREATE RECEIPT,OLD BENEFACTOR: " + receiptDTO.getBenefactor());
			benefactor = userService.findById(receiptDTO.getBenefactor().getId());
		} else if (StringUtils.isNotBlank(receiptDTO.getBenefactor().getName())) {
			logger.info("########### CREATE RECEIPT,NEW BENEFACTOR: " + receiptDTO.getBenefactor());
			Date birthDate = null;
			if (receiptDTO.getBenefactor().getAge() > 0) {
				birthDate = DateUtils.addYears(new Date(), -receiptDTO.getBenefactor().getAge());
			}
			benefactor = userService.createNewBenefactor(receiptDTO.getBenefactor().getName(),
					receiptDTO.getBenefactor().getMobileNumber(), receiptDTO.getBenefactor().getGender(), birthDate,
					delegate, null);
		}

		receipt.setDonatorPhoneNumber(receiptDTO.getBenefactor().getName());
		receipt.setDonatorName(receiptDTO.getBenefactor().getMobileNumber());

		if (benefactor != null) {
			receipt.setBenefactor(benefactor);
		}

		if (delegate.getBranchId() != null)
			receipt.setBranch(new Branch(delegate.getBranchId()));
		if (delegate.getCityId() != null)
			receipt.setCity(new City(delegate.getCityId()));
		if (delegate.getRegionId() != null)
			receipt.setRegion(new Region(delegate.getRegionId()));
		if (delegate.getLocationId() != null)
			receipt.setLocation(new Location(delegate.getLocationId()));

		BigDecimal totalAmountForReceipt = new BigDecimal(0);
		ReceiptDetail receiptDetail = null;
		if (CollectionUtils.isNotEmpty(receiptDTO.getCouponsList())) {
			for (NewCouponDTO couponDTO : receiptDTO.getCouponsList()) {
				receiptDetail = new ReceiptDetail();
				receiptDetail.setNotes(couponDTO.getNotes());
				receiptDetail.setReceipt(receipt);
				receiptDetail.setPaymentType(receiptDTO.getPaymentType());
				receiptDetail.setAmount(couponDTO.getAmount());
				receiptDetail.setCreatedBy(receipt.getCreatedBy());
				receiptDetail.setCoupon(new Coupon(couponDTO.getCouponTypeId()));

				if (benefactor != null) {
					receiptDetail.setBenefactor(benefactor);
				}

				receiptDetail.setName(couponDTO.getCouponTypeName());
				receiptDetail.setTransactionType(TransactionTypeEnum.COUPON.getValue());

				receipt.getReceiptDetailsList().add(receiptDetail);
				totalAmountForReceipt = totalAmountForReceipt.add(couponDTO.getAmount());
			}
		}

		receipt.setTotalAmount(totalAmountForReceipt);
		receipt.setTotalPaid(totalAmountForReceipt);
		ReceiptPaymentDTO paymentDTO = receiptDTO.getReceiptPaymentDTO();
		ReceiptPayment receiptPayment = new ReceiptPayment();
		receiptPayment.setReceipt(receipt);
		receiptPayment.setCashValue(receipt.getTotalAmount());
		receiptPayment.setPaymentType(receiptDTO.getPaymentType());
		if (receiptDTO.getPaymentType() == PaymentTypeEnum.CHEQUE) {
			// receiptPayment.setBankCode(paymentDTO.getBankCode());
			receiptPayment.setChequeDate(GeneralUtils.parseDate(paymentDTO.getChequeDate()));
			receiptPayment.setChequeNumber(paymentDTO.getChequeNumber());
		} else if (receiptDTO.getPaymentType() == PaymentTypeEnum.CREDIT) {
			receiptPayment.setCreditCardTransactionNumber(paymentDTO.getCreditCardTransactionNumber());
		} else if (receiptDTO.getPaymentType() == PaymentTypeEnum.BANK_TRANSFER) {
			// receiptPayment.setDeductionNumber(paymentDTO.getDeductionNumber());
			// receiptPayment.setBankCode(paymentDTO.getBankCode());
			// receiptPayment.setAccountName(paymentDTO.getAccountName());
			// receiptPayment.setAccountNumber(paymentDTO.getAccountNumber());
		}
		receiptPayment.setCreatedBy(receipt.getCreatedBy());
		receipt.setReceiptPayment(receiptPayment);
		return receipt;
	}

	private String findReceiptDetailName(ReceiptDetail receiptDetails) {
		String name = "";
		name = receiptDetails.getCoupon().getName();
		return name;
	}

	public ReceiptDetailDTO convertReceiptDetailToDTO(ReceiptDetail receiptDetail, String lang) {
		ReceiptDetailDTO receiptDetailDTO = new ReceiptDetailDTO();

		if (StringUtils.isBlank(receiptDetailDTO.getPaymentType())) {
			receiptDetailDTO.setPaymentType(receiptDetail.getReceipt().getPaymentType().toString());
		}
		receiptDetailDTO.setPaymentTypeDisplay(receiptDetailDTO.getPaymentType());
		receiptDetailDTO.setAmount(receiptDetail.getAmount());

		receiptDetailDTO.setName(receiptDetail.getName());
		if (receiptDetail.getReceiptId() != null)
			receiptDetailDTO.setReceiptId(receiptDetail.getReceiptId().toString());
		receiptDetailDTO.setDate(GeneralUtils.formatDateTime(receiptDetail.getCreationDate(), lang));
		if (StringUtils.isBlank(receiptDetailDTO.getName())) {
			receiptDetailDTO.setName(findReceiptDetailName(receiptDetail));
		}
		receiptDetailDTO.setTotalAmount(receiptDetailDTO.getTotalAmount().add(receiptDetailDTO.getAmount()));
		receiptDetailDTO.setType(TransactionTypeEnum.COUPON.getValue());
		return receiptDetailDTO;
	}

	public ReceiptsReportDTO convertReceiptDetailsToReceiptsReportDTO(List<ReceiptDetail> list, String lang) {
		ReceiptsReportDTO receiptsReportDTO = new ReceiptsReportDTO();
		for (ReceiptDetail receiptDetail : list) {
			ReceiptDetailDTO receiptDetailDTO = convertReceiptDetailToDTO(receiptDetail, lang);
			if (receiptDetailDTO.getPaymentType().equals(PaymentTypeEnum.BANK_TRANSFER.toString())) {
				receiptsReportDTO.getBankTransferList().add(receiptDetailDTO);
			} else if (receiptDetailDTO.getPaymentType().equals(PaymentTypeEnum.CASH.toString())) {
				receiptsReportDTO.getCashList().add(receiptDetailDTO);
			} else if (receiptDetailDTO.getPaymentType().equals(PaymentTypeEnum.CHEQUE.toString())) {
				receiptsReportDTO.getChequeList().add(receiptDetailDTO);
			} else if (receiptDetailDTO.getPaymentType().equals(PaymentTypeEnum.CREDIT.toString())) {
				receiptsReportDTO.getCreditCardList().add(receiptDetailDTO);
			}

		}

		// calculating totals
		for (ReceiptDetailDTO obj : receiptsReportDTO.getCashList()) {
			receiptsReportDTO.setCashAmount(receiptsReportDTO.getCashAmount().add(obj.getTotalAmount()));
		}
		for (ReceiptDetailDTO obj : receiptsReportDTO.getChequeList()) {
			receiptsReportDTO.setChequeAmount(receiptsReportDTO.getChequeAmount().add(obj.getTotalAmount()));
		}
		for (ReceiptDetailDTO obj : receiptsReportDTO.getCreditCardList()) {
			receiptsReportDTO.setCreditCardAmount(receiptsReportDTO.getCreditCardAmount().add(obj.getTotalAmount()));
		}
		for (ReceiptDetailDTO obj : receiptsReportDTO.getBankTransferList()) {
			receiptsReportDTO
					.setBankTransferAmount(receiptsReportDTO.getBankTransferAmount().add(obj.getTotalAmount()));
		}
		receiptsReportDTO.setTotalAmount(receiptsReportDTO.getCashAmount().add(receiptsReportDTO.getChequeAmount())
				.add(receiptsReportDTO.getCreditCardAmount()).add(receiptsReportDTO.getBankTransferAmount()));
		return receiptsReportDTO;
	}

	public List<ReceiptDetailDTO> convertReceiptDetailsListToDTO(List<ReceiptDetail> list, String lang) {
		List<ReceiptDetailDTO> newList = new ArrayList<ReceiptDetailDTO>(list.size());
		for (ReceiptDetail receiptDetail : list) {
			newList.add(convertReceiptDetailToDTO(receiptDetail, lang));
		}
		return newList;
	}

	public ReceiptPrintDTO getReceiptPrint(Receipt receipt, String lang, boolean isPrint) {

		if (receipt == null)
			return new ReceiptPrintDTO();
		List<ReceiptDetailDTO> details = convertReceiptDetailsListToDTO(receipt.getReceiptDetailsList(), lang);
		ReceiptPrintDTO receiptPrintDTO = new ReceiptPrintDTO();
		BigDecimal totalAmount = receiptPrintDTO.getTotalAmount();
		for (ReceiptDetailDTO receiptDetailDTO : details) {
			totalAmount = totalAmount.add(receiptDetailDTO.getTotalAmount());
			receiptDetailDTO.setReceiptId(receipt.getId().toString());
		}
		receiptPrintDTO.setNumberOfPrints(receipt.getNumberOfPrints());
		receiptPrintDTO.setTotalAmount(totalAmount);
		receiptPrintDTO.setDetails(details);
		receiptPrintDTO.setReceiptNumber(receipt.getId().toString());
		receiptPrintDTO.setDonatorMobile(receipt.getDonatorPhoneNumber());
		receiptPrintDTO.setDonatorName(receipt.getDonatorName());
		receiptPrintDTO.setPaymentType(receipt.getPaymentType().toString());
		receiptPrintDTO.setPaymentTypeDisplay(receipt.getPaymentType().toString());
		receiptPrintDTO.setReceiptDate(GeneralUtils.formatDateTime(receipt.getCreationDate(), lang));
		receiptRepository.updateNumberOfPrints(receipt.getId());
		return receiptPrintDTO;
	}

	public CouponTypeDTO convertCouponToDTO(Coupon coupon) {
		CouponTypeDTO couponDTO = new CouponTypeDTO();
		couponDTO.setId(coupon.getId().toString());
		couponDTO.setMinimumAmount(coupon.getMinimumAmount());
		couponDTO.setMustEnterDonator(coupon.isMustEnterDonator());
		couponDTO.setName(coupon.getName());
		couponDTO.setPriority(coupon.getPriority());
		couponDTO.setQrCode(coupon.getQrCode());
		couponDTO.setValue(coupon.getValue());
		couponDTO.setVersion(coupon.getVersion());
		if (coupon.getCouponTypeEnum() == CouponTypeEnum.YEARLY) {
			couponDTO.setType(CouponTypeEnum.YEARLY);
		} else if (coupon.getCouponTypeEnum() == CouponTypeEnum.QUICK_PAY) {
			couponDTO.setType(CouponTypeEnum.QUICK_PAY);
		}
		return couponDTO;
	}

	public List<CouponTypeDTO> convertCouponListToDTO(List<Coupon> couponsList) {
		List<CouponTypeDTO> list = new ArrayList<>(couponsList.size());
		for (Coupon coupon : couponsList) {
			CouponTypeDTO couponDTO = convertCouponToDTO(coupon);
			list.add(couponDTO);
		}
		return list;
	}

	public List<CouponReportDTO> convertReceiptDetailsToCouponReportDTO(List<ReceiptDetail> list, String lang) {
		List<CouponReportDTO> resultList = new ArrayList<CouponReportDTO>();
		for (ReceiptDetail receiptDetail : list) {
			if (receiptDetail.getCouponId() != null && receiptDetail.getCouponId() > 0) {
				Coupon coupon = utilsService.getCouponFromCache(receiptDetail.getCouponId());
				CouponReportDTO couponReportDTONew = new CouponReportDTO(coupon, receiptDetail.getAmount());
				if (!resultList.contains(couponReportDTONew)) {
					resultList.add(couponReportDTONew);
				} else {
					CouponReportDTO couponReportDTOExisting = resultList.get(resultList.indexOf(couponReportDTONew));
					couponReportDTOExisting
							.setAmount(couponReportDTOExisting.getAmount().add(couponReportDTONew.getAmount()));
				}
			}
		}
		return resultList;
	}

	public SupervisorReportDTO convertReceiptListToSupervisorReport(List<Receipt> receiptList) {
		SupervisorReportDTO supervisorReportDTO = new SupervisorReportDTO();
		supervisorReportDTO.setDate(GeneralUtils.formatDateTime(new Date()));
		for (Receipt receipt : receiptList) {
			if (receipt.getPaymentType().equals(PaymentTypeEnum.CASH)) {
				supervisorReportDTO.setCashAmount(supervisorReportDTO.getCashAmount().add(receipt.getTotalAmount()));
				supervisorReportDTO.setCashReceiptsCount(supervisorReportDTO.getCashReceiptsCount() + 1);
			} else if (receipt.getPaymentType().equals(PaymentTypeEnum.CHEQUE)) {
				supervisorReportDTO
						.setChequeAmount(supervisorReportDTO.getChequeAmount().add(receipt.getTotalAmount()));
				supervisorReportDTO.setChequeReceiptsCount((supervisorReportDTO.getChequeReceiptsCount() + 1));
			} else if (receipt.getPaymentType().equals(PaymentTypeEnum.CREDIT)) {
				supervisorReportDTO
						.setCreditCardAmount(supervisorReportDTO.getCreditCardAmount().add(receipt.getTotalAmount()));
				supervisorReportDTO.setCreditCardReceiptsCount((supervisorReportDTO.getCreditCardReceiptsCount() + 1));
			}
			supervisorReportDTO.setTotalAmount(supervisorReportDTO.getCashAmount()
					.add(supervisorReportDTO.getChequeAmount()).add(supervisorReportDTO.getCreditCardAmount()));
		}
		return supervisorReportDTO;
	}

}
