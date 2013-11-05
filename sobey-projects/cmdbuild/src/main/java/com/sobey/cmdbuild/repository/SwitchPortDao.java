package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.SwitchPort;

public interface SwitchPortDao extends PagingAndSortingRepository<SwitchPort, Integer>,
		JpaSpecificationExecutor<SwitchPort> {
	List<SwitchPort> findAllByStatus(Character character);

	SwitchPort findByCodeAndStatus(String code, Character character);
}