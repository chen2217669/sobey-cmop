package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.FimasPortHistory;

public interface FimasPortHistoryDao extends PagingAndSortingRepository<FimasPortHistory, Integer>,
		JpaSpecificationExecutor<FimasPortHistory> {

}