package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.ServerPort;

public interface ServerPortDao extends PagingAndSortingRepository<ServerPort, Integer>,
		JpaSpecificationExecutor<ServerPort> {
	List<ServerPort> findAllByStatus(Character character);

	ServerPort findByCodeAndStatus(String code, Character character);
}