package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.IpaddressHistory;

public interface IpaddressHistoryDao extends PagingAndSortingRepository<IpaddressHistory, Integer>,
		JpaSpecificationExecutor<IpaddressHistory> {

}