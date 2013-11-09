package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.NicPortHistory;

public interface NicPortHistoryDao extends PagingAndSortingRepository<NicPortHistory, Integer>,
		JpaSpecificationExecutor<NicPortHistory> {

}