package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.Es3SpecHistory;

public interface Es3SpecHistoryDao extends PagingAndSortingRepository<Es3SpecHistory, Integer>,
		JpaSpecificationExecutor<Es3SpecHistory> {
	List<Es3SpecHistory> findAllByStatus(Character character);

	Es3SpecHistory findByCodeAndStatus(String code, Character character);
}