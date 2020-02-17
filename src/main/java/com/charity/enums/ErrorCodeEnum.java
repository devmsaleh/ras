package com.charity.enums;

public enum ErrorCodeEnum {

	SUCCESS_CODE(0), SYSTEM_ERROR_CODE(-1), WRONG_CREDENTIALS(1), USER_DISABLED(2), USER_EXPIRED(3),
	INVALID_PAYMENT_TYPE(4), DELEGATE_NOT_EXIST(5), RECEIPT_DETAILS_REQUIRED(6), COUPON_TYPE_NOT_EXIST(7),
	COUPON_AMOUNT_LESS_MINIMUM(8), DONATOR_NAME_PHONE_REQUIRED(9), COUPON_AMOUNT_REQUIRED(10),
	CREDIT_CARD_TRANSACTION_NUMBER_REQUIRED(11), ALL_CHEQUE_DETAILS_REQUIRED(12), MISSING_NEW_PROJECT_INFO(13),
	COUNTRY_NOT_EXIST(14), PROJECT_TYPE_NOT_EXIST(15), PROJECT_STUDY_NOT_EXIST(16), DONATOR_NOT_EXIST(17),
	NEW_DONATOR_DETAILS_REQUIRED(18), MISSING_DONATE_CURRENT_PROJECT_INFO(19), OLD_PROJECT_NOT_EXIST(20),
	MISSING_NEW_SPONSORSHIP_INFO(21), GIFT_TYPE_NOT_EXIST(22), SPONSOR_NOT_EXIST(23), DELEGATE_ID_REQUIRED(24),
	COUPON_TYPE_ID_REQUIRED(25), GIFT_AMOUNT_REQUIRED(26), GIFT_TYPE_ID_REQUIRED(27), ORPHAN_AMOUNT_REQUIRED(28),
	ORPHAN_ID_REQUIRED(29), BANK_CODE_AND_ACCOUNT_NUMBER_REQUIRED(30), MISSING_OLD_SPONSORSHIP_INFO(31),
	FIRST_TITLE_NOT_EXIST(32), CHARITY_BOX_IN_USE(33), CHARITY_BOX_SAME_SOURCE_EXISTS(34), BARCODE_NOT_EXIST(35),
	ACCESS_DENIED(36), SUBLOCATION_ALREADY_HAS_CHARITYBOX(37), NEW_CHARITYBOX_NOT_EXIST_IN_DB(38),
	NEW_CHARITYBOX_MUST_BE_INACTIVE(39), ONLY_ACTIVE_CHARITYBOX_CAN_BE_COLLECTED(40),
	CANNOT_REPLACE_CHARITYBOX_WITH_ITSELF(41), CANNOT_CHECK_CHARITY_BOX(42), CHARITYBOX_NOT_EXIST_IN_DB(43),
	ONLY_ACTIVE_CHARITYBOX_CAN_BE_REMOVED(44), REGION_ALREADY_EXIST(45), LOCATION_ALREADY_EXIST(46),
	SUBLOCATION_ALREADY_EXIST(47), CHARITYBOX_NUMBER_NOT_EXIST(48), ORPHAN_AMOUNT_OR_GIFT_REQUIRED(49),
	COUPON_QRCODE_NOT_EXIST(50), NO_PERMISSION_TO_USE_MOBILE(51);

	private int errorCode;

	public int intValue() {
		return errorCode;
	}

	public String stringValue() {
		return String.valueOf(errorCode);
	}

	private ErrorCodeEnum(int errorCode) {
		this.errorCode = errorCode;
	}

}