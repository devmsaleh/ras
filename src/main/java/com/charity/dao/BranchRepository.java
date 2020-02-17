package com.charity.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.charity.entities.Branch;
import com.charity.util.Constants;

public interface BranchRepository extends JpaRepository<Branch, Long> {

	@Query("from Branch order by nameArabic")
	List<Branch> findAllBranches();

	List<Branch> findByCityIdOrderByNameArabic(Long cityId);

	@Cacheable(cacheNames = Constants.CACHE_NAME_ALL_BRANCHES)
	@Query("from Branch")
	List<Branch> getAllBranches();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_ALL_BRANCHES, allEntries = true)
	void clearAllBranchesCache();
}
