package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.FimasBoxHistory;

public interface FimasBoxHistoryDao extends PagingAndSortingRepository<FimasBoxHistory, Integer>,
		JpaSpecificationExecutor<FimasBoxHistory> {

	List<FimasBoxHistory> findAllByStatus(Character character);

	FimasBoxHistory findByCodeAndStatus(String code, Character character);
}