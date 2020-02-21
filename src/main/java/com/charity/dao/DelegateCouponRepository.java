package com.charity.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.charity.entities.DelegateCoupon;

public interface DelegateCouponRepository extends JpaRepository<DelegateCoupon, BigInteger> {

	List<DelegateCoupon> findByDelegateId(long delegateId);

	@Modifying(clearAutomatically = true)
	@Transactional
	void deleteByDelegateIdAndCouponId(long delegateId, long couponId);
}
