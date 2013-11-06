package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.SwitchPortHistory;

public interface SwitchPortHistoryDao extends PagingAndSortingRepository<SwitchPortHistory, Integer>,
		JpaSpecificationExecutor<SwitchPortHistory> {

	List<SwitchPortHistory> findAllByStatus(Character character);

	SwitchPortHistory findByCodeAndStatus(String code, Character character);
}