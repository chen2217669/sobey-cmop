package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.EipPolicyHistory;

public interface EipPolicyHistoryDao extends PagingAndSortingRepository<EipPolicyHistory, Integer>,
		JpaSpecificationExecutor<EipPolicyHistory> {

}