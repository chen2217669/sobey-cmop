package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.NicHistory;

public interface NicHistoryDao extends PagingAndSortingRepository<NicHistory, Integer>,
		JpaSpecificationExecutor<NicHistory> {

}