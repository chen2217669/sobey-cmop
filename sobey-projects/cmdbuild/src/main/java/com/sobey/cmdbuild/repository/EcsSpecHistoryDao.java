package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.EcsSpecHistory;

public interface EcsSpecHistoryDao extends PagingAndSortingRepository<EcsSpecHistory, Integer>,
		JpaSpecificationExecutor<EcsSpecHistory> {

	List<EcsSpecHistory> findAllByStatus(Character character);

	EcsSpecHistory findByCodeAndStatus(String code, Character character);
}