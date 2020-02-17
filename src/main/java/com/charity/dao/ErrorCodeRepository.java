package com.charity.dao;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.charity.entities.ErrorCode;
import com.charity.util.Constants;

public interface ErrorCodeRepository extends CrudRepository<ErrorCode, Long> {

	@Cacheable(cacheNames = Constants.CACHE_NAME_ERROR_CODE)
	@Query(value = "from ErrorCode where id=:id")
	ErrorCode findErrorCodeById(@Param("id") Long id);

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_ERROR_CODE, allEntries = true)
	void clearErrorCodesCache();

}
