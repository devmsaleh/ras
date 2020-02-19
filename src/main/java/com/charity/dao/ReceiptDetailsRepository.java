package com.charity.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.ReceiptDetail;

public interface ReceiptDetailsRepository extends JpaRepository<ReceiptDetail, Long> {

	List<ReceiptDetail> findByCreatedByIdAndCreationDateAfterAndCreationDateBeforeOrderByIdAsc(Long delegateId,
			Date fromDate, Date toDate);

	List<ReceiptDetail> findByCreatedByIdAndCreationDateGreaterThanEqualAndCreationDateLessThanEqualOrderByIdAsc(
			Long delegateId, Date fromDate, Date toDate);

	List<ReceiptDetail> findByReceiptId(Long receiptId);
}
