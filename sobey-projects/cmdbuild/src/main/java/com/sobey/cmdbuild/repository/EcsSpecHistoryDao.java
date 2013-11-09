package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.EcsSpecHistory;

public interface EcsSpecHistoryDao extends PagingAndSortingRepository<EcsSpecHistory, Integer>,
		JpaSpecificationExecutor<EcsSpecHistory> {

}