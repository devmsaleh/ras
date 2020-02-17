package com.charity.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.charity.entities.Coupon;
import com.charity.entities.Receipt;
import com.charity.entities.User;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

	@Query(value = "select sum(totalAmount) from Receipt where delegate.id=:delegateId and collected=false")
	BigDecimal findDelegateTotalAmountNotCollected(@Param("delegateId") Long delegateId);

	@Query(value = "select sum(totalAmount) from Receipt where delegate.id=:delegateId and collected=true and creationDate > :creationDate")
	BigDecimal findDelegateTotalAmountCollectedFromDate(@Param("delegateId") Long delegateId,
			@Param("creationDate") Date creationDate);

	@Query(value = "select sum(detail.amount) from ReceiptDetail detail inner join Receipt r on r.id = detail.receipt.id where detail.coupon.id=:couponId and r.collected=true and r.creationDate > :creationDate")
	BigDecimal findCouponTotalAmountCollectedFromDate(@Param("couponId") Long couponId,
			@Param("creationDate") Date creationDate);

	List<Receipt> findByCollectedFalseOrderByIdDesc();

	List<Receipt> findByCollectedAndCreatedById(boolean collected, Long delegateId);

	List<Receipt> findByCollectedFalseAndBranchIdOrderByIdDesc(Long branchId);

	@Query(value = "from Receipt where delegate.id=:delegateId and collected=false order by id desc")
	List<Receipt> findDelegateNotCollectedReceipts(@Param("delegateId") Long delegateId);

	@Modifying(clearAutomatically = true)
	@Query(value = "update Receipt set numberOfPrints=numberOfPrints+1 where id=:id")
	@Transactional
	int updateNumberOfPrints(@Param("id") Long id);

	Receipt findByIdAndCreatedById(Long id, Long delegateId);

	Receipt findTop1ByCreatedByIdOrderByIdDesc(Long delegateId);

	@Query(value = "select sum(totalAmount) from Receipt where collected=false and creationDate > :creationDate")
	BigDecimal findTotalAmountNotCollected(@Param("creationDate") Date creationDate);

	@Query(value = "select sum(totalAmount) from Receipt where collected=true and creationDate > :creationDate")
	BigDecimal findTotalAmountCollected(@Param("creationDate") Date creationDate);

	@Query(value = "select count(*) from Receipt where creationDate > :creationDate")
	Long findReceiptsCount(@Param("creationDate") Date creationDate);

	@Query(value = "select distinct new com.charity.entities.User(r.delegate.id,r.delegate.displayName) from Receipt r where r.creationDate > :creationDate and r.collected=true")
	List<User> findDelegatesProductivityFromDate(@Param("creationDate") Date creationDate);

	@Query(value = "select distinct new com.charity.entities.Coupon(detail.coupon.id,detail.coupon.name) from ReceiptDetail detail inner join Receipt r on r.id = detail.receipt.id  where r.creationDate > :creationDate and r.collected=true")
	List<Coupon> findCouponsProductivityFromDate(@Param("creationDate") Date creationDate);

}
