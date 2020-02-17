package com.charity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.charity.entities.Emarah;

public interface EmarahRepository extends JpaRepository<Emarah, Long> {

	@Query(value = "from Emarah order by nameArabic")
	List<Emarah> findAllEmarahsOrderByName();

	@Query(value = "from Emarah where active=true order by nameArabic")
	List<Emarah> findActiveEmarahs();

}
