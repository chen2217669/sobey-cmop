package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.EcsHistory;

public interface EcsHistoryDao extends PagingAndSortingRepository<EcsHistory, Integer>,
		JpaSpecificationExecutor<EcsHistory> {
	List<EcsHistory> findAllByStatus(Character character);

	EcsHistory findByCodeAndStatus(String code, Character character);
}