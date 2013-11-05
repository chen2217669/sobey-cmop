package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.LoadBalancer;

public interface LoadBalancerDao extends PagingAndSortingRepository<LoadBalancer, Integer>,
		JpaSpecificationExecutor<LoadBalancer> {
	List<LoadBalancer> findAllByStatus(Character character);

	LoadBalancer findByCodeAndStatus(String code, Character character);
}