package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.DnsHistory;

public interface DnsHistoryDao extends PagingAndSortingRepository<DnsHistory, Integer>,
		JpaSpecificationExecutor<DnsHistory> {

}