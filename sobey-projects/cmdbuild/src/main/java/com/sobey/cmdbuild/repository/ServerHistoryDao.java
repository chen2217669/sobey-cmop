package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.ServerHistory;

public interface ServerHistoryDao extends PagingAndSortingRepository<ServerHistory, Integer>,
		JpaSpecificationExecutor<ServerHistory> {

	List<ServerHistory> findAllByStatus(Character character);

	ServerHistory findByCodeAndStatus(String code, Character character);
}