package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.EsgPolicyHistory;

public interface EsgPolicyHistoryDao extends PagingAndSortingRepository<EsgPolicyHistory, Integer>,
		JpaSpecificationExecutor<EsgPolicyHistory> {

}