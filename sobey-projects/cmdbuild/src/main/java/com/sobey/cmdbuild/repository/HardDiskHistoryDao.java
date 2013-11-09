package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.HardDiskHistory;

public interface HardDiskHistoryDao extends PagingAndSortingRepository<HardDiskHistory, Integer>,
		JpaSpecificationExecutor<HardDiskHistory> {

}