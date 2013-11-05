package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.LoadBalancerPort;

public interface LoadBalancerPortDao extends PagingAndSortingRepository<LoadBalancerPort, Integer>,
		JpaSpecificationExecutor<LoadBalancerPort> {
	List<LoadBalancerPort> findAllByStatus(Character character);

	LoadBalancerPort findByCodeAndStatus(String code, Character character);
}