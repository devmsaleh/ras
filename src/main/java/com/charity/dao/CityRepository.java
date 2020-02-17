package com.charity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.City;

public interface CityRepository extends JpaRepository<City, Long> {

	List<City> findByEmarahIdOrderByNameArabic(Long emarahId);

	List<City> findByEmarahIdAndActiveTrueOrderByNameArabic(Long emarahId);

}
