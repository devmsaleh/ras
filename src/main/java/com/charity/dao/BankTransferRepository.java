package com.charity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.BankTransfer;

public interface BankTransferRepository extends JpaRepository<BankTransfer, Long> {

}
