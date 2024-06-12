package com.charity.webservice;

import java.math.BigDecimal;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.charity.dto.ValidateCreateReceiptResult;
import com.charity.entities.Coupon;
import com.charity.enums.ErrorCodeEnum;
import com.charity.enums.PaymentTypeEnum;
import com.charity.util.GeneralUtils;
import com.charity.webservice.dto.NewCouponDTO;
import com.charity.webservice.dto.ReceiptDTO;
import com.charity.webservice.dto.ReceiptPaymentDTO;

@Component
public class ServiceValidation extends CharityServiceBase {

	public ValidateCreateReceiptResult validateCreateReceipt(ReceiptDTO receiptDTO) {

		ErrorCodeEnum errorCode = validateReceiptPayment(receiptDTO);
		if (isError(errorCode)) {
			return new ValidateCreateReceiptResult(errorCode);
		}

		if (CollectionUtils.isNotEmpty(receiptDTO.getCouponsList())) {
			for (NewCouponDTO couponDTO : receiptDTO.getCouponsList()) {
				errorCode = validateCouponData(couponDTO, receiptDTO.getBenefactor().getName());
				if (isError(errorCode)) {
					if (errorCode == ErrorCodeEnum.COUPON_TYPE_NOT_ACTIVE) {
						Coupon coupon = utilsService.getCouponFromCache(couponDTO.getCouponTypeId());
						return new ValidateCreateReceiptResult(errorCode, coupon);
					} else {
						return new ValidateCreateReceiptResult(errorCode);
					}
				}
			}
		}

		return new ValidateCreateReceiptResult(ErrorCodeEnum.SUCCESS_CODE);

	}

	public ErrorCodeEnum validateCouponData(NewCouponDTO newCouponDTO, String donatorName) {

		if (GeneralUtils.isEmptyNumber(newCouponDTO.getAmount())) {
			return ErrorCodeEnum.COUPON_AMOUNT_REQUIRED;
		}

		if (GeneralUtils.isEmptyNumber(newCouponDTO.getCouponTypeId())) {
			return ErrorCodeEnum.COUPON_TYPE_ID_REQUIRED;
		}

		Coupon coupon = utilsService.getCouponFromCache(newCouponDTO.getCouponTypeId());
		if (coupon == null || coupon.getId() == null) {
			return ErrorCodeEnum.COUPON_TYPE_NOT_EXIST;
		}

		if (!coupon.isActive()) {
			return ErrorCodeEnum.COUPON_TYPE_NOT_ACTIVE;
		}

		BigDecimal minimumAmount = coupon.getMinimumAmount();
		if (minimumAmount != null && newCouponDTO.getAmount().compareTo(minimumAmount) < 0) {
			return ErrorCodeEnum.COUPON_AMOUNT_LESS_MINIMUM;
		}

		return ErrorCodeEnum.SUCCESS_CODE;
	}

	public ErrorCodeEnum validateReceiptPayment(ReceiptDTO receiptDTO) {
		if (CollectionUtils.isEmpty(receiptDTO.getCouponsList())) {
			return ErrorCodeEnum.RECEIPT_DETAILS_REQUIRED;
		}

		if (GeneralUtils.isEmptyNumber(receiptDTO.getDelegateId())) {
			return ErrorCodeEnum.DELEGATE_ID_REQUIRED;
		}

		if (receiptDTO.getPaymentType() == PaymentTypeEnum.CREDIT
				&& StringUtils.isBlank(receiptDTO.getReceiptPaymentDTO().getCreditCardTransactionNumber())) {
			return ErrorCodeEnum.CREDIT_CARD_TRANSACTION_NUMBER_REQUIRED;
		}

		if (receiptDTO.getPaymentType() == PaymentTypeEnum.BANK_TRANSFER) {
			if (StringUtils.isBlank(receiptDTO.getReceiptPaymentDTO().getBankCode())
					|| StringUtils.isBlank(receiptDTO.getReceiptPaymentDTO().getAccountNumber()))
				return ErrorCodeEnum.BANK_CODE_AND_ACCOUNT_NUMBER_REQUIRED;
		}

		if (receiptDTO.getPaymentType() == PaymentTypeEnum.CHEQUE) {
			ReceiptPaymentDTO payment = receiptDTO.getReceiptPaymentDTO();
			if (StringUtils.isBlank(payment.getBankCode()) || StringUtils.isBlank(payment.getChequeNumber())
					|| StringUtils.isBlank(payment.getChequeDate()))
				return ErrorCodeEnum.ALL_CHEQUE_DETAILS_REQUIRED;
		}

		if (!EnumUtils.isValidEnum(PaymentTypeEnum.class, receiptDTO.getPaymentType().toString())) {
			return ErrorCodeEnum.INVALID_PAYMENT_TYPE;
		}

		if (!userRepository.existsById(receiptDTO.getDelegateId())) {
			return ErrorCodeEnum.DELEGATE_NOT_EXIST;
		}

		return ErrorCodeEnum.SUCCESS_CODE;
	}

}
