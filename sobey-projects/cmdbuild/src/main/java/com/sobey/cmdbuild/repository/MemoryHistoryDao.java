package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.MemoryHistory;

public interface MemoryHistoryDao extends PagingAndSortingRepository<MemoryHistory, Integer>,
		JpaSpecificationExecutor<MemoryHistory> {

	List<MemoryHistory> findAllByStatus(Character character);

	MemoryHistory findByCodeAndStatus(String code, Character character);
}