package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.EsgPolicyHistory;

public interface EsgPolicyHistoryDao extends PagingAndSortingRepository<EsgPolicyHistory, Integer>,
		JpaSpecificationExecutor<EsgPolicyHistory> {

	List<EsgPolicyHistory> findAllByStatus(Character character);

	EsgPolicyHistory findByCodeAndStatus(String code, Character character);
}