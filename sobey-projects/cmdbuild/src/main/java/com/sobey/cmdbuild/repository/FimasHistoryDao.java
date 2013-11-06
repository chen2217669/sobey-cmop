package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.FimasHistory;

public interface FimasHistoryDao extends PagingAndSortingRepository<FimasHistory, Integer>,
		JpaSpecificationExecutor<FimasHistory> {

	List<FimasHistory> findAllByStatus(Character character);

	FimasHistory findByCodeAndStatus(String code, Character character);
}