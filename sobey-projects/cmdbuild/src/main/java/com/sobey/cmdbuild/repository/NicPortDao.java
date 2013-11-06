package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.NicPort;

public interface NicPortDao extends PagingAndSortingRepository<NicPort, Integer>, JpaSpecificationExecutor<NicPort> {
	List<NicPort> findAllByStatus(Character character);

	NicPort findByCodeAndStatus(String code, Character character);
}