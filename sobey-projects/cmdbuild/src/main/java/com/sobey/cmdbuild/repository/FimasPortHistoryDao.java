package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.FimasPortHistory;

public interface FimasPortHistoryDao extends PagingAndSortingRepository<FimasPortHistory, Integer>,
		JpaSpecificationExecutor<FimasPortHistory> {

	List<FimasPortHistory> findAllByStatus(Character character);

	FimasPortHistory findByCodeAndStatus(String code, Character character);
}