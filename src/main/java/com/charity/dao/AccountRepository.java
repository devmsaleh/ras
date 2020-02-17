package com.charity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
