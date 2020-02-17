package com.charity.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.charity.entities.UserToken;

public interface TokenRepository extends JpaRepository<UserToken, String> {

	@Modifying(clearAutomatically = true)
	@Query(value = "update UserToken set expiryDate=:expirationDate where token=:token")
	int updateTokenExpirationDate(@Param("token") String token, @Param("expirationDate") Date expirationDate);

	@Modifying(clearAutomatically = true)
	@Query(value = "delete from UserToken where expiryDate <=:givenDate")
	@Transactional
	int deleteOldTokens(@Param("givenDate") Date givenDate);

	@Query("select count(*) from UserToken where token=:token and expiryDate > :date")
	long isValidToken(@Param("token") String token, @Param("date") Date date);

}
