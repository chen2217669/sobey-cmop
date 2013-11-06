package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.FirewallPort;

public interface FirewallPortDao extends PagingAndSortingRepository<FirewallPort, Integer>,
		JpaSpecificationExecutor<FirewallPort> {

	List<FirewallPort> findAllByStatus(Character character);

	FirewallPort findByCodeAndStatus(String code, Character character);
}