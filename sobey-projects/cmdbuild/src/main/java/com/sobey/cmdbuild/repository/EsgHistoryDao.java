package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.EsgHistory;

public interface EsgHistoryDao extends PagingAndSortingRepository<EsgHistory, Integer>,
		JpaSpecificationExecutor<EsgHistory> {

}