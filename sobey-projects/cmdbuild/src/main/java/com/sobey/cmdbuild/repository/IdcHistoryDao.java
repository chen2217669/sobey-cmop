package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.IdcHistory;

public interface IdcHistoryDao extends PagingAndSortingRepository<IdcHistory, Integer>,
		JpaSpecificationExecutor<IdcHistory> {

}