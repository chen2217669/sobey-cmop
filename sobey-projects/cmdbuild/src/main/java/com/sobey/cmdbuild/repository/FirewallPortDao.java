package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.FirewallPort;

public interface FirewallPortDao extends PagingAndSortingRepository<FirewallPort, Integer>,
		JpaSpecificationExecutor<FirewallPort> {

}