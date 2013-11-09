package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.LoadBalancerPort;

public interface LoadBalancerPortDao extends PagingAndSortingRepository<LoadBalancerPort, Integer>,
		JpaSpecificationExecutor<LoadBalancerPort> {

}