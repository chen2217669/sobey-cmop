package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.FirewallHistory;

public interface FirewallHistoryDao extends PagingAndSortingRepository<FirewallHistory, Integer>,
		JpaSpecificationExecutor<FirewallHistory> {

}