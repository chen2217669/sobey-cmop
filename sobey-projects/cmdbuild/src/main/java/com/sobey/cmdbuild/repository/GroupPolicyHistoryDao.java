package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.GroupPolicyHistory;

public interface GroupPolicyHistoryDao extends PagingAndSortingRepository<GroupPolicyHistory, Integer>,
		JpaSpecificationExecutor<GroupPolicyHistory> {

	List<GroupPolicyHistory> findAllByStatus(Character character);

	GroupPolicyHistory findByCodeAndStatus(String code, Character character);
}