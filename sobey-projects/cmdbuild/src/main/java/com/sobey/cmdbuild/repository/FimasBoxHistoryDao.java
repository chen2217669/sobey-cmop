package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.FimasBoxHistory;

public interface FimasBoxHistoryDao extends PagingAndSortingRepository<FimasBoxHistory, Integer>,
		JpaSpecificationExecutor<FimasBoxHistory> {

}