package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.NicPortHistory;

public interface NicPortHistoryDao extends PagingAndSortingRepository<NicPortHistory, Integer>,
		JpaSpecificationExecutor<NicPortHistory> {
	List<NicPortHistory> findAllByStatus(Character character);

	NicPortHistory findByCodeAndStatus(String code, Character character);
}