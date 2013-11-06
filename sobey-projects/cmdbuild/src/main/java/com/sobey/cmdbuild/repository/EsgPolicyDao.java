package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.EsgPolicy;

public interface EsgPolicyDao extends PagingAndSortingRepository<EsgPolicy, Integer>,
		JpaSpecificationExecutor<EsgPolicy> {

	List<EsgPolicy> findAllByStatus(Character character);

	EsgPolicy findByCodeAndStatus(String code, Character character);
}