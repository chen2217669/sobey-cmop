package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.NetappPortHistory;

public interface NetappPortHistoryDao extends PagingAndSortingRepository<NetappPortHistory, Integer>,
		JpaSpecificationExecutor<NetappPortHistory> {

}