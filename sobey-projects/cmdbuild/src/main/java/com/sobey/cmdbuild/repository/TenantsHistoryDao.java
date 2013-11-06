package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.TenantsHistory;

public interface TenantsHistoryDao extends PagingAndSortingRepository<TenantsHistory, Integer>,
		JpaSpecificationExecutor<TenantsHistory> {

	List<TenantsHistory> findAllByStatus(Character character);

	TenantsHistory findByCodeAndStatus(String code, Character character);
}