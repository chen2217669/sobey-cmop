package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.NetappBoxHistory;

public interface NetappBoxHistoryDao extends PagingAndSortingRepository<NetappBoxHistory, Integer>,
		JpaSpecificationExecutor<NetappBoxHistory> {

}