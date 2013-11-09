package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Cs2History;

public interface Cs2HistoryDao extends PagingAndSortingRepository<Cs2History, Integer>,
		JpaSpecificationExecutor<Cs2History> {

}