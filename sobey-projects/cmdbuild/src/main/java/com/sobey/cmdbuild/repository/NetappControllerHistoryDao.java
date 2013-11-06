package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.NetappControllerHistory;

public interface NetappControllerHistoryDao extends PagingAndSortingRepository<NetappControllerHistory, Integer>,
		JpaSpecificationExecutor<NetappControllerHistory> {

	List<NetappControllerHistory> findAllByStatus(Character character);

	NetappControllerHistory findByCodeAndStatus(String code, Character character);
}