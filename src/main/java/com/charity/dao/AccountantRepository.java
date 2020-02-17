package com.charity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.charity.entities.Accountant;

public interface AccountantRepository extends JpaRepository<Accountant, Long> {

	@Query(value = "from Accountant where active=true order by name")
	List<Accountant> findAllActiveAccountants();

	@Query(value = "from Accountant  order by name")
	List<Accountant> findAllAccountants();

}
