package com.charity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.CollectEntry;

public interface CollectEntryRepository extends JpaRepository<CollectEntry, Long> {

	List<CollectEntry> findByCollectedByFinanceFalse();

	List<CollectEntry> findByCollectedByFinanceFalseAndBranchId(Long branchId);

}
