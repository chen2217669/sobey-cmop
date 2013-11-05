package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.Switches;

public interface SwitchesDao extends PagingAndSortingRepository<Switches, Integer>, JpaSpecificationExecutor<Switches> {
	List<Switches> findAllByStatus(Character character);

	Switches findByCodeAndStatus(String code, Character character);
}