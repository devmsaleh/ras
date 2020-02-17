package com.charity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

	List<Location> findByRegionIdOrderByNameArabic(Long regionId);

	List<Location> findByRegionIdAndActiveTrueOrderByNameArabic(Long regionId);
}
