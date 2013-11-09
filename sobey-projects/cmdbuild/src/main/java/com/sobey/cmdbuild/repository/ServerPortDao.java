package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.ServerPort;

public interface ServerPortDao extends PagingAndSortingRepository<ServerPort, Integer>,
		JpaSpecificationExecutor<ServerPort> {

}