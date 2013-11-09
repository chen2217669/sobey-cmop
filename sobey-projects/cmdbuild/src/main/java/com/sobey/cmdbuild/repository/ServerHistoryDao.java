package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.ServerHistory;

public interface ServerHistoryDao extends PagingAndSortingRepository<ServerHistory, Integer>,
		JpaSpecificationExecutor<ServerHistory> {

}