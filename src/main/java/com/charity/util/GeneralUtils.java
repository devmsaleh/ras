package com.charity.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.PrimeFaces;

public class GeneralUtils {

	public static final String DATE_FORMAT_FOR_DISPLAY = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT_FOR_DISPLAY_ARABIC = "yyyy-MM-dd  hh:mm a";
	public static final String TIME_FORMAT_FOR_DISPLAY_ARABIC = "hh:mm a";
	public static final String DATE_TIME_FORMAT_FOR_DISPLAY_ENGLISH = "dd-MM-yyyy  hh:mm a";
	public static final String DATE_FORMAT_INPUT_BY_USER = "yyyyMMdd";

	private static List<String> colorsList;

	static {

		colorsList = new ArrayList<String>(30);
		colorsList.add("#4BB2C5");
		colorsList.add("#EAA228");
		colorsList.add("#C5B47F");
		colorsList.add("#579575");
		colorsList.add("#CC6666");
		colorsList.add("#E7E658");
		colorsList.add("#93B75F");
		colorsList.add("#66CC66");
		colorsList.add("#FF6384");
		colorsList.add("#4BC0C0");
		colorsList.add("#FFCD56");
		colorsList.add("#C9CBCF");
		colorsList.add("#B4AE59");
		colorsList.add("#579575");
		colorsList.add("#4BB2C5");
		colorsList.add("#953579");
		colorsList.add("#39add1");
		colorsList.add("#3079ab");
		colorsList.add("#c25975");
		colorsList.add("#e15258");
		colorsList.add("#f9845b");
		colorsList.add("#838cc7");
		colorsList.add("#7d669e");
		colorsList.add("#53bbb4");
		colorsList.add("#51b46d");
		colorsList.add("#e0ab18");
		colorsList.add("#637a91");
		colorsList.add("#f092b0");
		colorsList.add("#b7c0c7");

	}

	public static String getNewLine() {
		return System.getProperty("line.separator");
	}

	public static String formatDate(Date date) {
		if (date == null)
			return "";
		try {
			String lang = "ar";
			String dateFormat = DATE_TIME_FORMAT_FOR_DISPLAY_ARABIC;
			if (lang.equals("en")) {
				dateFormat = DATE_TIME_FORMAT_FOR_DISPLAY_ENGLISH;
			}
			Locale locale = new Locale(lang);
			return new SimpleDateFormat(dateFormat, locale).format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return date.toString();
		}
	}

	public static String formatDateOnly(Date date) {
		if (date == null)
			return "";
		try {
			String lang = "ar";
			String dateFormat = DATE_FORMAT_FOR_DISPLAY;
			if (lang.equals("en")) {
				dateFormat = DATE_FORMAT_FOR_DISPLAY;
			}
			Locale locale = new Locale(lang);
			return new SimpleDateFormat(dateFormat, locale).format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return date.toString();
		}
	}

	public static String formatTimeOnly(Date date) {
		if (date == null)
			return "";
		try {
			String lang = "ar";
			String dateFormat = TIME_FORMAT_FOR_DISPLAY_ARABIC;
			if (lang.equals("en")) {
				dateFormat = TIME_FORMAT_FOR_DISPLAY_ARABIC;
			}
			Locale locale = new Locale(lang);
			return new SimpleDateFormat(dateFormat, locale).format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return date.toString();
		}
	}

	public static HttpServletRequest getHttpServletRequest() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return request;
	}

	public static void addInfoMessage(String message, String id) {
		addMessage(message, id, FacesMessage.SEVERITY_INFO);
	}

	public static void addErrorMessage(String message, String id) {
		addMessage(message, id, FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().validationFailed();
	}

	public static void addMessage(String message, String id, Severity severity) {
		String formId = "form";
		String clientId = null;
		if (StringUtils.isNotBlank(id) && !id.contains(":")) {
			clientId = formId + ":" + id;
		}
		FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, message, message));
		if (StringUtils.isNotBlank(id)) {
			PrimeFaces.current().scrollTo(clientId);
		}
	}

	public static void redirect(String url) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String redirectUrl = ec.getRequestContextPath() + url;
		try {
			ec.redirect(redirectUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getDefaultAvatarPath() {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		return servletContext.getRealPath("/resources/images/defaultAvatar.png");
	}

	public static void showDialogError(String message) {
		FacesContext.getCurrentInstance().validationFailed();
		PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_FATAL, "خطأ", message),
				false);
	}

	public static void showDialogInfo(String message) {
		PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "تنبيه", message),
				false);
	}

	public static boolean isEmptyNumber(Object obj) {
		if (obj == null)
			return true;
		if (obj instanceof BigDecimal) {
			BigDecimal number = (BigDecimal) obj;
			return number.compareTo(BigDecimal.ZERO) <= 0;
		}
		if (obj instanceof BigInteger) {
			BigInteger number = (BigInteger) obj;
			return number.compareTo(BigInteger.ZERO) <= 0;
		}
		if (obj instanceof Long) {
			Long number = (Long) obj;
			return number <= 0;
		}
		if (obj instanceof Integer) {
			Integer number = (Integer) obj;
			return number <= 0;
		}
		if (obj instanceof String) {
			try {
				return Long.parseLong(obj.toString()) <= 0;
			} catch (Exception e) {
				return true;
			}
		}
		return true;

	}

	public static Date parseDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_INPUT_BY_USER);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String formatDateTime(Date date) {
		return formatDateTime(date, "ar");
	}

	public static String formatDateTime(Date date, String lang) {
		if (date == null)
			return "";
		String format = DATE_TIME_FORMAT_FOR_DISPLAY_ARABIC;
		if (StringUtils.isBlank(lang)) {
			lang = "ar";
		}
		if (lang.equalsIgnoreCase("en")) {
			format = DATE_TIME_FORMAT_FOR_DISPLAY_ENGLISH;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale(lang));
		try {
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String generateRandomColor() {
		Random random = new Random();
		return String.format("#%06x", random.nextInt(256 * 256 * 256));
	}

	public static String getColor(int index) {
		if (index < colorsList.size() - 1) {
			return colorsList.get(index);
		} else
			return generateRandomColor();
	}

	public static int generateRandomNumber(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	public static String getRandomNumberWithSize(int size) {
		String ranValue = "";
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			int randomNumber = rand.nextInt(10);
			if (i == 0 && randomNumber == 0) {
				randomNumber = rand.nextInt(10);
			}
			ranValue += String.valueOf(randomNumber);
		}
		return ranValue;
	}

	public static String generateRandomUserName() {
		return String.valueOf(getRandomNumberWithSize(6));
	}

	public static int getAge(Date birthday) {
		if (birthday == null)
			return 0;
		int age = 0;
		try {
			DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			int d1 = Integer.parseInt(formatter.format(birthday));
			int d2 = Integer.parseInt(formatter.format(new Date()));
			age = (d2 - d1) / 10000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return age;
	}

	public static void main(String[] args) {
		Date birthDay = DateUtils.addYears(new Date(), -31);
		System.out.println("######### birthDay: " + birthDay);
		System.out.println("####### AGE: " + getAge(birthDay));
	}

}
