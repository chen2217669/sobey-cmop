package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.ServerPortHistory;

public interface ServerPortHistoryDao extends PagingAndSortingRepository<ServerPortHistory, Integer>,
		JpaSpecificationExecutor<ServerPortHistory> {
	List<ServerPortHistory> findAllByStatus(Character character);

	ServerPortHistory findByCodeAndStatus(String code, Character character);
}