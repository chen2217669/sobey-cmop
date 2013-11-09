package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Es3SpecHistory;

public interface Es3SpecHistoryDao extends PagingAndSortingRepository<Es3SpecHistory, Integer>,
		JpaSpecificationExecutor<Es3SpecHistory> {

}