package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.RackHistory;

public interface RackHistoryDao extends PagingAndSortingRepository<RackHistory, Integer>,
		JpaSpecificationExecutor<RackHistory> {

	List<RackHistory> findAllByStatus(Character character);

	RackHistory findByCodeAndStatus(String code, Character character);
}