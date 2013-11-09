package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.SwitchPortHistory;

public interface SwitchPortHistoryDao extends PagingAndSortingRepository<SwitchPortHistory, Integer>,
		JpaSpecificationExecutor<SwitchPortHistory> {

}