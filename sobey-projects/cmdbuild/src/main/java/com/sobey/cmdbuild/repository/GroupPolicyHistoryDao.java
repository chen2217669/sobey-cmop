package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.GroupPolicyHistory;

public interface GroupPolicyHistoryDao extends PagingAndSortingRepository<GroupPolicyHistory, Integer>,
		JpaSpecificationExecutor<GroupPolicyHistory> {

}