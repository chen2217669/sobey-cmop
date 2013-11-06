package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.Cs2;

public interface Cs2Dao extends PagingAndSortingRepository<Cs2, Integer>, JpaSpecificationExecutor<Cs2> {
	List<Cs2> findAllByStatus(Character character);

	Cs2 findByCodeAndStatus(String code, Character character);
}