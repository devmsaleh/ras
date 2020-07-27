package com.charity.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.charity.dao.ReceiptRepository;
import com.charity.dao.UserRepository;
import com.charity.entities.Coupon;
import com.charity.entities.Receipt;
import com.charity.entities.ReceiptDashboard;
import com.charity.entities.User;
import com.charity.service.UtilsService;
import com.charity.util.GeneralUtils;

@Component("dashboardBean")
@Scope("view")
public class DashboardBean implements Serializable {

	private static final long serialVersionUID = 5475272767494091100L;

	private static final Logger log = LoggerFactory.getLogger(DashboardBean.class);

	private List<ReceiptDashboard> receiptDashboardList = new ArrayList<ReceiptDashboard>();

	@Autowired
	private ReceiptRepository receiptRepository;

	@Autowired
	private UtilsService utilsService;

	@Autowired
	private CurrentUserBean currentUserBean;

	@Autowired
	private UserRepository userRepository;

	private PieChartModel pieModel1;

	private BigDecimal delegatesTotalNotCollectedAmount = new BigDecimal(0);

	private BarChartModel delegatesBarModel = new BarChartModel();

	private BarChartModel couponsBarModel = new BarChartModel();

	private Long receiptsCount;

	private Long onlineDelegatesCount;

	private BigDecimal notCollectedAmount = new BigDecimal(0);

	private BigDecimal collectedAmount = new BigDecimal(0);

	private String delegatesBarWidth = "50%";
	private String delegatesBarHeight = "450px";
	private String couponsBarWidth = "50%";
	private String couponsBarHeight = "450px";
	private Map<String, Number> delegatesChartMap = new LinkedHashMap<String, Number>();
	private Map<String, Number> couponsChartMap = new LinkedHashMap<String, Number>();

	@PostConstruct
	public void init() {
		try {
			List<Receipt> receiptsList = new ArrayList<Receipt>();
			if (currentUserBean.isAdmin()) {
				receiptsList = utilsService.findLatestReceipts();
			} else if (currentUserBean.isEmployee()) {
				receiptsList = utilsService.findLatestReceiptsByBranchId(currentUserBean.getUser().getBranchId());
			}
			for (Receipt receipt : receiptsList) {
				BigDecimal delegateNotCollectedAmount = null;
				ReceiptDashboard receiptDashboardExist = isDelegateExists(receipt.getDelegate().getId());
				if (receiptDashboardExist != null) {
					// log.info("######## receiptDashboardExist for delegate: " +
					// receipt.getDelegate().getId());
					delegateNotCollectedAmount = receiptDashboardExist.getDelegateNotCollectedAmount();
				} else {
					delegateNotCollectedAmount = receiptRepository
							.findDelegateTotalAmountNotCollected(receipt.getDelegate().getId());
					delegatesTotalNotCollectedAmount = delegatesTotalNotCollectedAmount.add(delegateNotCollectedAmount);
				}
				receiptDashboardList.add(new ReceiptDashboard(receipt, delegateNotCollectedAmount));
			}
			log.info("########### receiptList: " + receiptsList.size());

			generateCharts();

		} catch (Exception e) {
			log.error("Exception in init DashboardBean", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private void generateCharts() {

		Date oneMonthBefore = DateUtils.addDays(new Date(), -30);
		oneMonthBefore = DateUtils.truncate(oneMonthBefore, Calendar.DAY_OF_MONTH);
		log.info("######## oneMonthBefore: " + oneMonthBefore);
		receiptsCount = receiptRepository.findReceiptsCount(oneMonthBefore);
		Date eightHoursBefore = DateUtils.addHours(new Date(), -8);
		log.info("######## eightHoursBefore: " + eightHoursBefore);
		onlineDelegatesCount = userRepository.findActiveDelegatesCount(eightHoursBefore);
		log.info("######## onlineDelegatesCount: " + onlineDelegatesCount);
		collectedAmount = receiptRepository.findTotalAmountCollected(oneMonthBefore);
		notCollectedAmount = receiptRepository.findTotalAmountNotCollected(oneMonthBefore);

		List<User> delegatesProductivityList = receiptRepository.findDelegatesProductivityFromDate(oneMonthBefore);
		log.info("######## delegatesProductivityList: " + delegatesProductivityList.size());

		List<Coupon> couponsProductivityList = receiptRepository.findCouponsProductivityFromDate(oneMonthBefore);
		log.info("######## couponsProductivityList: " + couponsProductivityList.size());

		delegatesChartMap = new LinkedHashMap<String, Number>();
		for (User user : delegatesProductivityList) {
			BigDecimal amount = receiptRepository.findDelegateTotalAmountCollectedFromDate(user.getId(),
					oneMonthBefore);
			if (amount == null) {
				amount = new BigDecimal(0);
			}
			delegatesChartMap.put(user.getDisplayName(), amount);
		}

		createBarModel(delegatesBarModel, "إيرادات المناديب آخر 30 يوم", delegatesChartMap);

		couponsChartMap = new LinkedHashMap<String, Number>();
		for (Coupon coupon : couponsProductivityList) {
			BigDecimal amount = receiptRepository.findCouponTotalAmountCollectedFromDate(coupon.getId(),
					oneMonthBefore);
			if (amount == null) {
				amount = new BigDecimal(0);
			}
			couponsChartMap.put(coupon.getName(), amount);
		}
		if (couponsChartMap.size() > 10) {
			couponsBarWidth = "90%";
		}

		createBarModel(couponsBarModel, "إيرادات الكوبونات آخر 30 يوم", couponsChartMap);

		createPieModel1();
	}

	private void createBarModel(BarChartModel model, String titleText, Map<String, Number> dataMap) {

		model.setExtender("skinBar");
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("");

		List<Number> valuesList = new ArrayList<>();
		List<String> labelsList = new ArrayList<>();
		List<String> backgroundColorList = new ArrayList<>();

		int loopIndex = 0;
		for (Map.Entry<String, Number> entry : dataMap.entrySet()) {
			labelsList.add(entry.getKey());
			valuesList.add(entry.getValue());
			backgroundColorList.add(GeneralUtils.getColor(loopIndex));
			loopIndex++;
		}

		barDataSet.setData(valuesList);
		barDataSet.setBackgroundColor(backgroundColorList);

		data.addChartDataSet(barDataSet);
		data.setLabels(labelsList);
		model.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText(titleText);
		title.setFontFamily("\"GE SS Two Light\", Arial, sans-serif");
		title.setFontSize(17);
		options.setTitle(title);

		Legend legend = new Legend();
		legend.setDisplay(false);
		options.setLegend(legend);

		options.setBarThickness(5);
		options.setMaxBarThickness(6);

		model.setOptions(options);

	}

	private ReceiptDashboard isDelegateExists(Long delegateId) {
		for (ReceiptDashboard receiptDashboard : receiptDashboardList) {
			if (receiptDashboard.getDelegate().getId() == delegateId)
				return receiptDashboard;
		}
		return null;
	}

	private void createPieModel1() {
		pieModel1 = new PieChartModel();

		pieModel1.set("كفالة يتيم", 540);
		pieModel1.set("علاج مريض", 325);
		pieModel1.set("سقيا الماء", 702);
		pieModel1.set("بناء مسجد", 421);

		pieModel1.setTitle("إيرادات الكوبونات");
		pieModel1.setLegendPosition("w");
		pieModel1.setShadow(false);
	}

	public BigDecimal findDelegateTotalNotCollected(Long delegateId) {
		return new BigDecimal("100");
	}

	public PieChartModel getPieModel1() {
		return pieModel1;
	}

	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}

	public List<ReceiptDashboard> getReceiptDashboardList() {
		return receiptDashboardList;
	}

	public void setReceiptDashboardList(List<ReceiptDashboard> receiptDashboardList) {
		this.receiptDashboardList = receiptDashboardList;
	}

	public BigDecimal getDelegatesTotalNotCollectedAmount() {
		return delegatesTotalNotCollectedAmount;
	}

	public void setDelegatesTotalNotCollectedAmount(BigDecimal delegatesTotalNotCollectedAmount) {
		this.delegatesTotalNotCollectedAmount = delegatesTotalNotCollectedAmount;
	}

	public BarChartModel getDelegatesBarModel() {
		return delegatesBarModel;
	}

	public void setDelegatesBarModel(BarChartModel delegatesBarModel) {
		this.delegatesBarModel = delegatesBarModel;
	}

	public BarChartModel getCouponsBarModel() {
		return couponsBarModel;
	}

	public void setCouponsBarModel(BarChartModel couponsBarModel) {
		this.couponsBarModel = couponsBarModel;
	}

	public Long getReceiptsCount() {
		if (receiptsCount == null)
			receiptsCount = 0l;
		return receiptsCount;
	}

	public void setReceiptsCount(Long receiptsCount) {
		this.receiptsCount = receiptsCount;
	}

	public Long getOnlineDelegatesCount() {
		return onlineDelegatesCount;
	}

	public void setOnlineDelegatesCount(Long onlineDelegatesCount) {
		this.onlineDelegatesCount = onlineDelegatesCount;
	}

	public BigDecimal getCollectedAmount() {
		if (collectedAmount == null)
			collectedAmount = new BigDecimal(0);
		return collectedAmount;
	}

	public void setCollectedAmount(BigDecimal collectedAmount) {
		this.collectedAmount = collectedAmount;
	}

	public BigDecimal getNotCollectedAmount() {
		if (notCollectedAmount == null)
			notCollectedAmount = new BigDecimal(0);
		return notCollectedAmount;
	}

	public void setNotCollectedAmount(BigDecimal notCollectedAmount) {
		this.notCollectedAmount = notCollectedAmount;
	}

	public String getDelegatesBarWidth() {
		return delegatesBarWidth;
	}

	public void setDelegatesBarWidth(String delegatesBarWidth) {
		this.delegatesBarWidth = delegatesBarWidth;
	}

	public String getDelegatesBarHeight() {
		return delegatesBarHeight;
	}

	public void setDelegatesBarHeight(String delegatesBarHeight) {
		this.delegatesBarHeight = delegatesBarHeight;
	}

	public String getCouponsBarWidth() {
		return couponsBarWidth;
	}

	public void setCouponsBarWidth(String couponsBarWidth) {
		this.couponsBarWidth = couponsBarWidth;
	}

	public String getCouponsBarHeight() {
		return couponsBarHeight;
	}

	public void setCouponsBarHeight(String couponsBarHeight) {
		this.couponsBarHeight = couponsBarHeight;
	}

	public Map<String, Number> getDelegatesChartMap() {
		return delegatesChartMap;
	}

	public void setDelegatesChartMap(Map<String, Number> delegatesChartMap) {
		this.delegatesChartMap = delegatesChartMap;
	}

	public Map<String, Number> getCouponsChartMap() {
		return couponsChartMap;
	}

	public void setCouponsChartMap(Map<String, Number> couponsChartMap) {
		this.couponsChartMap = couponsChartMap;
	}

}
