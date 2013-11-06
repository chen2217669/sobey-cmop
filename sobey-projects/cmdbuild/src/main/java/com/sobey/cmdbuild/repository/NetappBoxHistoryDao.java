package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.NetappBoxHistory;

public interface NetappBoxHistoryDao extends PagingAndSortingRepository<NetappBoxHistory, Integer>,
		JpaSpecificationExecutor<NetappBoxHistory> {

	List<NetappBoxHistory> findAllByStatus(Character character);

	NetappBoxHistory findByCodeAndStatus(String code, Character character);
}