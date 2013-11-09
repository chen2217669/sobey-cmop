package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.As2History;

public interface As2HistoryDao extends PagingAndSortingRepository<As2History, Integer>,
		JpaSpecificationExecutor<As2History> {

}