package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.FirewallPortHistory;

public interface FirewallPortHistoryDao extends PagingAndSortingRepository<FirewallPortHistory, Integer>,
		JpaSpecificationExecutor<FirewallPortHistory> {

	List<FirewallPortHistory> findAllByStatus(Character character);

	FirewallPortHistory findByCodeAndStatus(String code, Character character);
}