package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.FimasPort;

public interface FimasPortDao extends PagingAndSortingRepository<FimasPort, Integer>,
		JpaSpecificationExecutor<FimasPort> {

}