package com.charity.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.charity.entities.ReceiptDetail;

public interface ReceiptDetailsRepository extends JpaRepository<ReceiptDetail, Long> {

	List<ReceiptDetail> findByCreatedByIdAndCreationDateAfterAndCreationDateBeforeOrderByIdAsc(Long delegateId,
			Date fromDate, Date toDate);

	List<ReceiptDetail> findByCreatedByIdAndCreationDateGreaterThanEqualAndCreationDateLessThanEqualOrderByIdAsc(
			Long delegateId, Date fromDate, Date toDate);

	List<ReceiptDetail> findByCreatedByIdAndReceiptCollectedAndCreationDateGreaterThanEqualAndCreationDateLessThanEqualOrderByIdAsc(
			Long delegateId, boolean collected, Date fromDate, Date toDate);

	List<ReceiptDetail> findByReceiptId(Long receiptId);

	@Query(value = "select distinct name from ReceiptDetail where benefactor.id=:benefactorId order by name")
	List<String> findBenefactorCoupouns(@Param("benefactorId") Long benefactorId);

	List<ReceiptDetail> findByBenefactorIdNotNullOrderByIdDesc();
}
