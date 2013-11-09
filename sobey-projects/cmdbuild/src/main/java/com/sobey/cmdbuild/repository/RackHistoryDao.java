package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.RackHistory;

public interface RackHistoryDao extends PagingAndSortingRepository<RackHistory, Integer>,
		JpaSpecificationExecutor<RackHistory> {

}