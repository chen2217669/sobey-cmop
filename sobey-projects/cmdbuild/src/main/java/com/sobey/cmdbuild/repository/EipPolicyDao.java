package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.EipPolicy;

public interface EipPolicyDao extends PagingAndSortingRepository<EipPolicy, Integer>,
		JpaSpecificationExecutor<EipPolicy> {
	List<EipPolicy> findAllByStatus(Character character);

	EipPolicy findByCodeAndStatus(String code, Character character);
}