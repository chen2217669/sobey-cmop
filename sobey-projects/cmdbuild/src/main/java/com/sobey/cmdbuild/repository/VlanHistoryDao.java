package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.VlanHistory;

public interface VlanHistoryDao extends PagingAndSortingRepository<VlanHistory, Integer>,
		JpaSpecificationExecutor<VlanHistory> {
	List<VlanHistory> findAllByStatus(Character character);

	VlanHistory findByCodeAndStatus(String code, Character character);
}