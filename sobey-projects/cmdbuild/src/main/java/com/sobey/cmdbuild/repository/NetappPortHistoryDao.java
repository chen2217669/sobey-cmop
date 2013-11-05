package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.NetappPortHistory;

public interface NetappPortHistoryDao extends PagingAndSortingRepository<NetappPortHistory, Integer>,
		JpaSpecificationExecutor<NetappPortHistory> {
	List<NetappPortHistory> findAllByStatus(Character character);

	NetappPortHistory findByCodeAndStatus(String code, Character character);
}