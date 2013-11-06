package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.DnsHistory;

public interface DnsHistoryDao extends PagingAndSortingRepository<DnsHistory, Integer>,
		JpaSpecificationExecutor<DnsHistory> {

	List<DnsHistory> findAllByStatus(Character character);

	DnsHistory findByCodeAndStatus(String code, Character character);
}