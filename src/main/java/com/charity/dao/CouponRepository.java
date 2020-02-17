package com.charity.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.charity.entities.Coupon;
import com.charity.util.Constants;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

	@Query(value = "select image from Coupon where id=:id")
	@Cacheable(cacheNames = Constants.CACHE_NAME_COUPON_IMAGE)
	byte[] getImageById(@Param("id") Long id);

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_COUPON_IMAGE, allEntries = true)
	void clearCouponImageCache();

	@Query(value = "from Coupon where active=true order by priority")
	List<Coupon> findAllActiveCouponsOrderByPriority();

	@Query(value = "from Coupon order by priority")
	List<Coupon> findAllCouponsOrderByPriority();

	@Query(value = "from Coupon where active=true order by name")
	List<Coupon> findAllActiveCouponsOrderByName();

	@Cacheable(cacheNames = Constants.CACHE_NAME_ALL_COUPONS)
	@Query("from Coupon")
	List<Coupon> getAllCoupons();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_ALL_COUPONS, allEntries = true)
	void clearAllCoupons();

	List<Coupon> findByNameStartingWithAndActiveTrue(String name);

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_COUPONS_TYPES, allEntries = true)
	void clearCouponsTypesCache();

	@Modifying(clearAutomatically = true)
	@Query(value = "update Coupon set image=:image where id=:id")
	@Transactional
	int updateCouponImage(@Param("id") Long id, @Param("image") byte[] image);

}
