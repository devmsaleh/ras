package com.charity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.FinanceEntry;

public interface FinanceEntryRepository extends JpaRepository<FinanceEntry, Long> {

}
