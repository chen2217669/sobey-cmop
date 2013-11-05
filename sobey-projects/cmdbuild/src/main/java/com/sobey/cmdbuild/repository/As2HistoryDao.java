package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.As2History;

public interface As2HistoryDao extends PagingAndSortingRepository<As2History, Integer>,
		JpaSpecificationExecutor<As2History> {
	List<As2History> findAllByStatus(Character character);

	As2History findByCodeAndStatus(String code, Character character);
}