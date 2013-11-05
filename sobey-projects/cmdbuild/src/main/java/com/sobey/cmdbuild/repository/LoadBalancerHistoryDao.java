package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.LoadBalancerHistory;

public interface LoadBalancerHistoryDao extends PagingAndSortingRepository<LoadBalancerHistory, Integer>,
		JpaSpecificationExecutor<LoadBalancerHistory> {
	List<LoadBalancerHistory> findAllByStatus(Character character);

	LoadBalancerHistory findByCodeAndStatus(String code, Character character);
}