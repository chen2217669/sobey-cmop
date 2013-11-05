package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.IdcHistory;

public interface IdcHistoryDao extends PagingAndSortingRepository<IdcHistory, Integer>,
		JpaSpecificationExecutor<IdcHistory> {
	List<IdcHistory> findAllByStatus(Character character);

	IdcHistory findByCodeAndStatus(String code, Character character);
}