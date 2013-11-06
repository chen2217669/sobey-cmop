package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.EipSpecHistory;

public interface EipSpecHistoryDao extends PagingAndSortingRepository<EipSpecHistory, Integer>,
		JpaSpecificationExecutor<EipSpecHistory> {

	List<EipSpecHistory> findAllByStatus(Character character);

	EipSpecHistory findByCodeAndStatus(String code, Character character);
}