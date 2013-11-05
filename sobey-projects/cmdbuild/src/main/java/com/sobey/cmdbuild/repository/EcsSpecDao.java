package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.EcsSpec;

public interface EcsSpecDao extends PagingAndSortingRepository<EcsSpec, Integer>, JpaSpecificationExecutor<EcsSpec> {
	List<EcsSpec> findAllByStatus(Character character);

	EcsSpec findByCodeAndStatus(String code, Character character);
}