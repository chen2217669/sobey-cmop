package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.SwitchesHistory;

public interface SwitchesHistoryDao extends PagingAndSortingRepository<SwitchesHistory, Integer>,
		JpaSpecificationExecutor<SwitchesHistory> {
	List<SwitchesHistory> findAllByStatus(Character character);

	SwitchesHistory findByCodeAndStatus(String code, Character character);
}