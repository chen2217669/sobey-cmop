package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Server;

public interface ServerDao extends PagingAndSortingRepository<Server, Integer>, JpaSpecificationExecutor<Server> {

}