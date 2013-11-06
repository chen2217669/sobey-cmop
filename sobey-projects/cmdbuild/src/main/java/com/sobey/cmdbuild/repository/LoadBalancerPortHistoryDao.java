package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.LoadBalancerPortHistory;

public interface LoadBalancerPortHistoryDao extends PagingAndSortingRepository<LoadBalancerPortHistory, Integer>,
		JpaSpecificationExecutor<LoadBalancerPortHistory> {

	List<LoadBalancerPortHistory> findAllByStatus(Character character);

	LoadBalancerPortHistory findByCodeAndStatus(String code, Character character);
}