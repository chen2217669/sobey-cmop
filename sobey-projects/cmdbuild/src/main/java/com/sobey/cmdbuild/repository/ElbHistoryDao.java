package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.ElbHistory;

public interface ElbHistoryDao extends PagingAndSortingRepository<ElbHistory, Integer>,
		JpaSpecificationExecutor<ElbHistory> {

}