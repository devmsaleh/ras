package com.charity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
