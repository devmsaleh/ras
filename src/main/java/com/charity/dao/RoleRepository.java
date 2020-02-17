package com.charity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

	List<Role> findByHiddenFalse();

}
