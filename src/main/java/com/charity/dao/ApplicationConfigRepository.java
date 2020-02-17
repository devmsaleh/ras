package com.charity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.ApplicationConfig;

public interface ApplicationConfigRepository extends JpaRepository<ApplicationConfig, Long> {

}
