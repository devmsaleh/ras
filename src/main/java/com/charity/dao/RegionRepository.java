package com.charity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {

	List<Region> findByCityIdOrderByNameArabic(Long cityId);

	List<Region> findByCityIdAndActiveTrueOrderByNameArabic(Long cityId);

}
