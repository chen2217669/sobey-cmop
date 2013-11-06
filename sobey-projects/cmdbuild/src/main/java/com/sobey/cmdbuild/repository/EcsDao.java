package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.Ecs;

public interface EcsDao extends PagingAndSortingRepository<Ecs, Integer>, JpaSpecificationExecutor<Ecs> {
	List<Ecs> findAllByStatus(Character character);

	Ecs findByCodeAndStatus(String code, Character character);
}