package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.ConsumptionsHistory;

public interface ConsumptionsHistoryDao extends PagingAndSortingRepository<ConsumptionsHistory, Integer>,
		JpaSpecificationExecutor<ConsumptionsHistory> {
	List<ConsumptionsHistory> findAllByStatus(Character character);

	ConsumptionsHistory findByCodeAndStatus(String code, Character character);
}