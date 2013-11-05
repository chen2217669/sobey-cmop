package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.FimasPort;

public interface FimasPortDao extends PagingAndSortingRepository<FimasPort, Integer>,
		JpaSpecificationExecutor<FimasPort> {
	List<FimasPort> findAllByStatus(Character character);

	FimasPort findByCodeAndStatus(String code, Character character);
}