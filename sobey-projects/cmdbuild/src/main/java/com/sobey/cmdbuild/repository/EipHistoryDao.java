package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.EipHistory;

public interface EipHistoryDao extends PagingAndSortingRepository<EipHistory, Integer>,
		JpaSpecificationExecutor<EipHistory> {

}