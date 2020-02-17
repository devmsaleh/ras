package com.charity.service;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charity.dao.BranchRepository;
import com.charity.dao.CouponRepository;
import com.charity.entities.Branch;
import com.charity.entities.Coupon;

@Service
public class CacheService {

	private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private CouponRepository couponRepository;

	public void refreshAllCaches() {
		DateTime startDate = DateTime.now();
		logger.info("######### refreshAllCaches started at: " + startDate);
		branchRepository.clearAllBranchesCache();
		List<Branch> allCompanyBranchesList = branchRepository.getAllBranches();
		logger.info("####### allCompanyBranchesList: " + allCompanyBranchesList.size());
		couponRepository.clearAllCoupons();
		List<Coupon> allCouponsList = couponRepository.getAllCoupons();
		couponRepository.clearCouponImageCache();
		logger.info("####### allCouponsList: " + allCouponsList.size());
		DateTime endDate = DateTime.now();
		int seconds = Seconds.secondsBetween(startDate, endDate).getSeconds();
		logger.info("######### refreshAllCaches ended at: " + endDate + ", it took " + seconds + " seconds");
	}

}
