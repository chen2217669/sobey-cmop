package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.CompanyHistory;

public interface CompanyHistoryDao extends PagingAndSortingRepository<CompanyHistory, Integer>,
		JpaSpecificationExecutor<CompanyHistory> {
	List<CompanyHistory> findAllByStatus(Character character);

	CompanyHistory findByCodeAndStatus(String code, Character character);
}