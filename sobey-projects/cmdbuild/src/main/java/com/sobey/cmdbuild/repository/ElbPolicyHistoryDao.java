package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.ElbPolicyHistory;

public interface ElbPolicyHistoryDao extends PagingAndSortingRepository<ElbPolicyHistory, Integer>,
		JpaSpecificationExecutor<ElbPolicyHistory> {

	List<ElbPolicyHistory> findAllByStatus(Character character);

	ElbPolicyHistory findByCodeAndStatus(String code, Character character);
}