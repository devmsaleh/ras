package com.charity.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.charity.dto.DelegateIncomeReportDTO;
import com.charity.entities.Coupon;
import com.charity.entities.ReceiptDetail;
import com.charity.entities.User;
import com.charity.enums.PaymentTypeEnum;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UtilsRepository {

	private static final Logger log = LoggerFactory.getLogger(UtilsRepository.class);

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<DelegateIncomeReportDTO> generateDelegatesIncomeTotalReport(Date fromDate, Date toDate, User delegate,
			Coupon coupon, Boolean collected) {

		List<DelegateIncomeReportDTO> resultList = new ArrayList<DelegateIncomeReportDTO>();
		boolean filterByCoupon = coupon != null && coupon.getId() != null && coupon.getId() > 0;
		StringBuffer sb = new StringBuffer();
		String queryStr = "select detail from Receipt r inner join ReceiptDetail detail on r.id = detail.receipt.id where 1=1";
		sb.append(queryStr);
		if (fromDate != null) {
			sb.append(" and r.receiptDate >=:fromDate");
		}

		if (toDate != null) {
			sb.append(" and r.receiptDate <=:toDate");
		}

		if (delegate != null && delegate.getId() != null) {
			sb.append(" and r.delegate.id=:delegateId");
		}

		if (filterByCoupon) {
			sb.append(" and detail.coupon.id=:couponId");
		}

		if (collected != null) {
			sb.append(" and r.collected=:collected");
		}

		log.info("############### generateDelegatesIncomeTotalReport query: " + sb.toString());

		Query query = entityManager.createQuery(sb.toString());

		if (fromDate != null) {
			query.setParameter("fromDate", fromDate);
		}

		if (toDate != null) {
			query.setParameter("toDate", toDate);
		}

		if (delegate != null && delegate.getId() != null) {
			query.setParameter("delegateId", delegate.getId());
		}

		if (coupon != null && coupon.getId() != null) {
			query.setParameter("couponId", coupon.getId());
		}

		if (collected != null) {
			query.setParameter("collected", collected.booleanValue());
		}

		List<ReceiptDetail> detailsList = query.getResultList();

		log.info("########## detailsList: " + detailsList.size());

		for (ReceiptDetail receiptDetail : detailsList) {

			DelegateIncomeReportDTO delegateIncomeReportDTO = new DelegateIncomeReportDTO(receiptDetail.getCreatedBy());
			int objectIndex = resultList.indexOf(delegateIncomeReportDTO);
			if (objectIndex >= 0) {
				delegateIncomeReportDTO = resultList.get(objectIndex);
			}

			if (receiptDetail.getPaymentType() == PaymentTypeEnum.CASH) {
				delegateIncomeReportDTO
						.setCashAmount(delegateIncomeReportDTO.getCashAmount().add(receiptDetail.getAmount()));
			} else if (receiptDetail.getPaymentType() == PaymentTypeEnum.CHEQUE) {
				delegateIncomeReportDTO
						.setChequeAmount(delegateIncomeReportDTO.getChequeAmount().add(receiptDetail.getAmount()));
			} else if (receiptDetail.getPaymentType() == PaymentTypeEnum.CREDIT) {
				delegateIncomeReportDTO.setCreditCardAmount(
						delegateIncomeReportDTO.getCreditCardAmount().add(receiptDetail.getAmount()));
			} else if (receiptDetail.getPaymentType() == PaymentTypeEnum.BANK_TRANSFER) {
				delegateIncomeReportDTO.setBankTransferAmount(
						delegateIncomeReportDTO.getBankTransferAmount().add(receiptDetail.getAmount()));
			}

			if (objectIndex < 0) {
				resultList.add(delegateIncomeReportDTO);
			}

		}

		log.info("########## resultList: " + resultList.size());

		return resultList;
	}

}
