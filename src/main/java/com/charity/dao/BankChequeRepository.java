package com.charity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.BankCheque;

public interface BankChequeRepository extends JpaRepository<BankCheque, Long> {

}
