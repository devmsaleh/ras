package com.charity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.CouponType;

public interface CouponTypeRepository extends JpaRepository<CouponType, Long> {

}
