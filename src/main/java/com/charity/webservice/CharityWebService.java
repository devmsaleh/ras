package com.charity.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.charity.entities.BankCheque;
import com.charity.entities.BankTransfer;
import com.charity.entities.Coupon;
import com.charity.entities.DelegateCoupon;
import com.charity.entities.Receipt;
import com.charity.entities.ReceiptDetail;
import com.charity.entities.User;
import com.charity.entities.UserToken;
import com.charity.enums.ErrorCodeEnum;
import com.charity.enums.RoleTypeEnum;
import com.charity.enums.UserTypeEnum;
import com.charity.util.GeneralUtils;
import com.charity.webservice.dto.BenefactorDTO;
import com.charity.webservice.dto.CouponTypeDTO;
import com.charity.webservice.dto.DelegateDTO;
import com.charity.webservice.dto.GeneralResponseDTO;
import com.charity.webservice.dto.ReceiptDTO;
import com.charity.webservice.dto.ReceiptPrintDTO;
import com.charity.webservice.dto.ReceiptsReportDTO;
import com.charity.webservice.dto.ServiceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Component
@Path("/v1")
@Api("")
@Transactional
public class CharityWebService extends CharityServiceBase {

	private static final Logger logger = LoggerFactory.getLogger(CharityWebService.class);

	@Value("${environment}")
	private String environmentStr;

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/login")
	@ApiOperation(value = "تسجيل الدخول")
	public ServiceResponse login(@HeaderParam("userName") String userName, @HeaderParam("password") String password,
			@HeaderParam("lang") String lang) throws Exception {
		try {
			logger.info("############# CharityWebService > login ################");
			User delegate = userService.authenticate(userName, password);
			if (delegate != null) {
				logger.info("############# delegate.isEnabled(): " + delegate.isEnabled());
			}
			if (delegate == null) {
				return new ServiceResponse(ErrorCodeEnum.WRONG_CREDENTIALS, errorCodeRepository, lang);
			} else if (!delegate.isEnabled()
					|| (delegate.getStartDate() != null && delegate.getStartDate().after(new Date()))) {
				return new ServiceResponse(ErrorCodeEnum.USER_DISABLED, errorCodeRepository, lang);
			} else if (delegate.getExpiryDate() != null && delegate.getExpiryDate().before(new Date())) {
				return new ServiceResponse(ErrorCodeEnum.USER_EXPIRED, errorCodeRepository, lang);
			} else if (!userService.isUserHasRole(RoleTypeEnum.ROLE_DELEGATE, delegate)) {
				return new ServiceResponse(ErrorCodeEnum.NO_PERMISSION_TO_USE_MOBILE, errorCodeRepository, lang);
			}
			UserToken token = new UserToken(delegate.getId());
			tokenRepository.save(token);
			delegate.setToken(token.getToken());
			delegate.setLastLoginDate(new Date());
			userRepository.save(delegate);
			DelegateDTO delegateDTO = convertDelegateToDTO(delegate);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, delegateDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in login webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/loginLocation")
	@ApiOperation(value = "تسجيل الدخول")
	public ServiceResponse loginLocation(@HeaderParam("userName") String userName,
			@HeaderParam("password") String password, @HeaderParam("lang") String lang) throws Exception {
		try {
			logger.info("############# CharityWebService > login ################");
			User delegate = userService.authenticate(userName, password);
			if (delegate != null) {
				logger.info("############# delegate.isEnabled(): " + delegate.isEnabled());
			}
			if (delegate == null) {
				return new ServiceResponse(ErrorCodeEnum.WRONG_CREDENTIALS, errorCodeRepository, lang);
			} else if (!delegate.isEnabled()
					|| (delegate.getStartDate() != null && delegate.getStartDate().after(new Date()))) {
				return new ServiceResponse(ErrorCodeEnum.USER_DISABLED, errorCodeRepository, lang);
			} else if (delegate.getExpiryDate() != null && delegate.getExpiryDate().before(new Date())) {
				return new ServiceResponse(ErrorCodeEnum.USER_EXPIRED, errorCodeRepository, lang);
			} else if (!userService.isUserHasRole(RoleTypeEnum.ROLE_DELEGATE, delegate)) {
				return new ServiceResponse(ErrorCodeEnum.NO_PERMISSION_TO_USE_MOBILE, errorCodeRepository, lang);
			}
			UserToken token = new UserToken(delegate.getId());
			tokenRepository.save(token);
			delegate.setToken(token.getToken());
			delegate.setLastLoginDate(new Date());
			userRepository.save(delegate);
			DelegateDTO delegateDTO = convertDelegateToDTO(delegate);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, delegateDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in login webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/logout")
	@ApiOperation(value = "تسجيل خروج")
	public ServiceResponse logout(@HeaderParam("delegateId") String delegateId,
			@HeaderParam("latitude") String latitudeParam, @HeaderParam("longitude") String longitudeParam,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, new GeneralResponseDTO(true), errorCodeRepository,
					lang);
		} catch (Exception e) {
			logger.error("Exception in logout webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/changePassword")
	@ApiOperation(value = "تغيير كلمة المرور")
	public ServiceResponse changePassword(@HeaderParam("delegateId") String delegateId,
			@HeaderParam("oldPassword") String oldPassword, @HeaderParam("newPassword") String newPassword,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, new GeneralResponseDTO(true), errorCodeRepository,
					lang);
		} catch (Exception e) {
			logger.error("Exception in changePassword webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getBanksForCheque")
	@ApiOperation(value = "عرض البنوك الخاصة بالشيكات")
	public ServiceResponse getBanksForCheque(@HeaderParam("token") String token, @HeaderParam("lang") String lang)
			throws Exception {
		try {

			List<BankCheque> banksList = bankChequeRepository.findAll();
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, banksList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in getBanksForCheque webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getBanksForTransfer")
	@ApiOperation(value = "عرض البنوك الخاصة بالتحويل البنكي")
	public ServiceResponse getBanksForTransfer(@HeaderParam("token") String token, @HeaderParam("lang") String lang)
			throws Exception {
		try {

			List<BankTransfer> banksList = bankTransferRepository.findAll();
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, banksList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in getBanksForTransfer webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/createReceipt")
	@ApiOperation(value = "انشاء ريسيت")
	public ServiceResponse createReceipt(ReceiptDTO receiptDTO, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			ErrorCodeEnum errorCode = validation.validateCreateReceipt(receiptDTO);
			if (isError(errorCode)) {
				logger.info("createReceipt Error " + errorCode.intValue() + " , JSON: "
						+ (new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(receiptDTO)));
				return new ServiceResponse(errorCode, errorCodeRepository, lang);
			}

			Receipt receipt = convertReceipDTOToReceipt(receiptDTO);
			receipt = receiptRepository.save(receipt);
			userRepository.updateLastActionDate(receiptDTO.getDelegateId());
			ReceiptPrintDTO receiptPrintDTO = getReceiptPrint(receipt, lang, true);
			boolean sendSMS = false;
			BenefactorDTO benefactorDTO = receiptDTO.getBenefactor();
			if (StringUtils.isNotBlank(environmentStr) && environmentStr.equalsIgnoreCase("Production")) {
				sendSMS = true;
			} else if (StringUtils.isNotBlank(benefactorDTO.getMobileNumber())
					&& benefactorDTO.getMobileNumber().equals("0566399945")) {
				sendSMS = true;
			}
			if (sendSMS) {
				if (StringUtils.isNotBlank(benefactorDTO.getMobileNumber())
						&& benefactorDTO.getMobileNumber().length() == 10) {
					sendSMS(benefactorDTO.getMobileNumber(), receiptPrintDTO.getTotalAmount().toString(),
							receipt.getId().toString());
				}
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, receiptPrintDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in createReceipt webservice: ", e);
			logger.error("Exception in createReceipt webservice, JSON: "
					+ (new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(receiptDTO)));
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findCouponByBarcode/{barcode}")
	@ApiOperation(value = "عرض تفاصيل الكوبون باستخدام الباركود")
	public ServiceResponse findCouponByBarcode(@PathParam("barcode") String barcode, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {
			Coupon couponType = utilsService.getCouponByBarcode(barcode);
			CouponTypeDTO couponTypeDTO = convertCouponToDTO(couponType);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, couponTypeDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findCouponByBarcode webservice for barcode: " + barcode + ",Exception is: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findCoupons/{name}")
	@ApiOperation(value = "البحث فى الكوبونات")
	public ServiceResponse findCoupons(@PathParam("name") String name, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			List<Coupon> couponsList = couponRepository.findByNameStartingWithAndActiveTrue(name);
			logger.info("###### findCoupons,couponsList: " + couponsList.size());
			List<CouponTypeDTO> resultList = convertCouponListToDTO(couponsList);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findCoupons webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findDelegateLatestReceipt/{delegateId}")
	@ApiOperation(value = "عرض تفاصيل أخر ايصال منشأ بواسطة مفوض")
	public ServiceResponse findDelegateLatestReceipt(@PathParam("delegateId") Long delegateId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			Receipt receipt = receiptRepository.findTop1ByCreatedByIdOrderByIdDesc(delegateId);
			ReceiptPrintDTO receiptPrintDTO = getReceiptPrint(receipt, lang, false);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, receiptPrintDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findReceiptDetails webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findDelegateReceipts/{delegateId}/{fromDate}/{toDate}")
	@ApiOperation(value = "عرض الايصالات التى أنشأها مفوض فى فترة معينة")

	public ServiceResponse findDelegateReceipts(@PathParam("delegateId") Long delegateId,
			@PathParam("fromDate") String fromDateStr, @PathParam("toDate") String toDateStr,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			Date fromDate = GeneralUtils.parseDate(fromDateStr);
			Date toDate = GeneralUtils.parseDate(toDateStr);
			toDate = DateUtils.setHours(toDate, 23);
			toDate = DateUtils.setMinutes(toDate, 59);
			toDate = DateUtils.setSeconds(toDate, 0);
			List<ReceiptDetail> list = receiptDetailsRepository
					.findByCreatedByIdAndCreationDateGreaterThanEqualAndCreationDateLessThanEqualOrderByIdAsc(
							delegateId, fromDate, toDate);

			ReceiptsReportDTO receiptsReportDTO = convertReceiptDetailsToReceiptsReportDTO(list, lang);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, receiptsReportDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findDelegateReceipts webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findDelegateTotalAmount/{delegateId}")
	@ApiOperation(value = "عرض رصيد المندوب")
	public ServiceResponse findDelegateTotalAmount(@PathParam("delegateId") Long delegateId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			BigDecimal amount = receiptRepository.findDelegateTotalAmountNotCollected(delegateId);
			if (amount == null) {
				amount = new BigDecimal(0);
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, amount, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findDelegateTotalAmount webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findReceiptDetails/{delegateId}/{receiptId}")
	@ApiOperation(value = "عرض تفاصيل ايصال منشأ بواسطة مفوض")
	public ServiceResponse findReceiptDetails(@PathParam("delegateId") Long delegateId,
			@PathParam("receiptId") Long receiptId, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {
			Receipt receipt = receiptRepository.findByIdAndCreatedById(receiptId, delegateId);
			ReceiptPrintDTO receiptPrintDTO = getReceiptPrint(receipt, lang, false);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, receiptPrintDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findReceiptDetails webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.WILDCARD)
	@Path("/getCouponImage/{couponId}")
	@ApiOperation(value = "تحميل صورة الكوبون")
	public Response getCouponImage(@PathParam("couponId") Long couponId, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			// CouponType coupon = couponRepository.findOne(couponId);
			byte[] image = couponRepository.getImageById(couponId);
			String mimeType = "image/png";
			mimeType = StringUtils.isEmpty(mimeType) ? "image/*" : mimeType;
			String fileName = "Coupon_" + couponId;
			if (!StringUtils.isEmpty(mimeType)) {
				if (mimeType.toLowerCase().contains("png"))
					fileName += ".png";
				else if (mimeType.toLowerCase().contains("jpg") || mimeType.toLowerCase().contains("jpeg"))
					fileName += ".jpg";
				else
					fileName += ".image";
			}
			return Response.ok(image, mimeType)
					.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
		} catch (Exception e) {
			logger.error("Exception in getCouponImage webservice: ", e);
			return Response.serverError().build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getCoupons/{delegateId}")
	@ApiOperation(value = "عرض انواع الكوبونات")
	public ServiceResponse getCoupons(@PathParam("delegateId") Long delegateId, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			List<Coupon> couponsList = couponRepository.getAllCoupons();
			List<DelegateCoupon> favoritesList = delegateCouponRepository.findByDelegateId(delegateId);

			List<CouponTypeDTO> resultList = convertCouponListToDTO(couponsList);
			for (CouponTypeDTO couponTypeDTO : resultList) {
				for (DelegateCoupon delegateCoupon : favoritesList) {
					if (delegateCoupon.getCouponId().toString().equals(couponTypeDTO.getId())) {
						couponTypeDTO.setFavorite(true);
					}
				}
			}
			resultList.sort(Comparator.comparing(CouponTypeDTO::isFavorite).reversed());

			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in getCoupons webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findBenefactor/{searchText}")
	@ApiOperation(value = "البحث عن المتبرع بجزء من الإسم او رقم الجوال يبدء ب")
	public ServiceResponse findBenefactor(@PathParam("searchText") String searchText,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			List<User> benefactorsList = new ArrayList<User>();
			if (StringUtils.isNotBlank(searchText)) {
				searchText = searchText.trim();
				if (StringUtils.isAlpha(searchText) || StringUtils.containsWhitespace(searchText)) {
					logger.info("######### findBenefactor by name,searchText: " + searchText);
					benefactorsList = userRepository.findByTypeAndEnabledTrueAndDisplayNameContainingOrderByDisplayName(
							UserTypeEnum.BENEFACTOR, searchText);
				} else if (StringUtils.isNumeric(searchText)) {
					logger.info("######### findBenefactor by mobile,searchText: " + searchText);
					benefactorsList = userRepository
							.findByTypeAndEnabledTrueAndMobileNumberStartingWithOrderByDisplayName(
									UserTypeEnum.BENEFACTOR, searchText);
				}
			}
			logger.info("###### findBenefactor,benefactorsList: " + benefactorsList.size());
			List<BenefactorDTO> resultList = convertBenefactorListToDTO(benefactorsList);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findBenefactor webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/refreshApplicationCache")
	@ApiOperation(value = "تحديث الكاش ميمورى الخاص بالتطبيق واعادة تحميل جميع القيم اللوكاب من الداتا بيز ووضعها فى الكاش ميمورى مره أخرى")
	public ServiceResponse refreshApplicationCache(@QueryParam("attr1") String userName,
			@QueryParam("attr2") String password, @HeaderParam("lang") String lang) throws Exception {
		try {
			User delegate = userService.authenticate(userName, password);
			if (delegate != null && delegate.isEnabled()) {
				cacheService.refreshAllCaches();
				return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, new GeneralResponseDTO(true),
						errorCodeRepository, lang);
			} else {
				return new ServiceResponse(ErrorCodeEnum.ACCESS_DENIED, errorCodeRepository, lang);
			}
		} catch (Exception e) {
			logger.error("Exception in refreshApplicationCache webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@Async
	public String sendSMS(String mobileNumber, String totalDonatedAmount, String receiptNumber) {

		if (true)
			return "";
		mobileNumber = "971" + mobileNumber.substring(1);
		HttpURLConnection connection = null;
		BufferedReader bufferedReader = null;
		String result = null;
		try {

			StringBuffer messageTextSb = new StringBuffer();
			messageTextSb.append("شكرا لتبرعكم لجمعية الإحسان الخيرية").append("\n").append("تم استلام مبلغ")
					.append(" ").append(totalDonatedAmount).append(" ").append("درهم").append("\n")
					.append("رقم الإيصال ").append(receiptNumber);
			String messageText = URLEncoder.encode(messageTextSb.toString(), "UTF-8");
			// http://fuj.smscharity.net:9980/smsgw.aspx?user=fuj191&pass=Passw0rd191$&ProviderID=1019&text=%D8%AA%D8%AC%D8%B1%D8%A8%D8%A9&msisdn=971504339373&encoding=2
			URL url = new URL(
					"http://fuj.smscharity.net:9980/smsgw.aspx?user=fuj191&pass=Passw0rd191$&ProviderID=1019&text="
							+ messageText + "&msisdn=" + mobileNumber + "&encoding=2");
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(1000 * 30);
			connection.setReadTimeout(1000 * 30);
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.connect();
			bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			result = sb.toString();
		} catch (Exception e) {
			logger.error("Exception in sendSMS: ", e);
		} finally {
			if (connection != null)
				connection.disconnect();
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e) {
					logger.error("Exception in sendSMS: ", e);
				}
		}
		return result;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/addToFavorites/{delegateId}/{couponId}")
	@ApiOperation(value = "اضافة كوبون الى المفضلة")
	public ServiceResponse addToFavorites(@PathParam("delegateId") String delegateId,
			@PathParam("couponId") String couponId, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			// we should make unique constraint on delegateId+couponId
			// also we should make check that coupoun is not already in favorites

			DelegateCoupon delegateCoupon = new DelegateCoupon(new User(new BigInteger(delegateId).longValue()),
					new Coupon(new BigInteger(couponId).longValue()));
			delegateCouponRepository.save(delegateCoupon);
			logger.info("######## addFavorite,id: " + delegateCoupon.getId());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, new GeneralResponseDTO(true), errorCodeRepository,
					lang);
		} catch (Exception e) {
			logger.error("Exception in addToFavorites webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/removeFromFavorites/{delegateId}/{couponId}")
	@ApiOperation(value = "إزالة كوبون من المفضلة")
	public ServiceResponse removeFromFavorites(@PathParam("delegateId") String delegateId,
			@PathParam("couponId") String couponId, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {
			delegateCouponRepository.deleteByDelegateIdAndCouponId(new BigInteger(delegateId).longValue(),
					new BigInteger(couponId).longValue());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, new GeneralResponseDTO(true), errorCodeRepository,
					lang);
		} catch (Exception e) {
			logger.error("Exception in removeFromFavorites webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}
}
