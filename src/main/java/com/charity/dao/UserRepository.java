package com.charity.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.charity.entities.User;
import com.charity.enums.UserTypeEnum;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserNameIgnoreCase(String username);

	User findByEmailIgnoreCase(String email);

	String findDisplayNameById(Long id);

	int countByUserNameIgnoreCase(String username);

	int countByEmailIgnoreCase(String email);

	int countByMobileNumber(String mobile);

	List<User> findByTypeOrderByUserOrder(UserTypeEnum type);

	List<User> findByTypeOrderByDateCreatedDesc(UserTypeEnum type);

	List<User> findByTypeAndBranchIdOrderByUserOrder(UserTypeEnum type, Long branchId);

	List<User> findByTypeIn(List<UserTypeEnum> types);

	List<User> findByBranchIdAndTypeIn(Long branchId, List<UserTypeEnum> types);

	List<User> findByTypeAndEnabledTrueAndDisplayNameAndMobileNumber(UserTypeEnum type, String displayName,
			String mobileNumber);

	List<User> findByTypeAndEnabledTrueAndDisplayNameContainingOrderByDisplayName(UserTypeEnum type,
			String displayName);

	List<User> findByTypeAndEnabledTrueAndMobileNumberStartingWithOrderByDisplayName(UserTypeEnum type,
			String mobileNumber);

	int countByTypeAndMobileNumber(UserTypeEnum type, String mobileNumber);

	@Modifying(clearAutomatically = true)
	@Query(value = "update User set lastActionDate=CURRENT_TIMESTAMP where id=:id")
	@Transactional
	int updateLastActionDate(@Param("id") Long id);

	@Query(value = "select count(1) from User where type=com.charity.enums.UserTypeEnum.DELEGATE and lastLoginDate > :date or lastActionDate > :date")
	long findActiveDelegatesCount(@Param("date") Date date);
}
