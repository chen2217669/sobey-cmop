package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.VpnHistory;

public interface VpnHistoryDao extends PagingAndSortingRepository<VpnHistory, Integer>,
		JpaSpecificationExecutor<VpnHistory> {
	List<VpnHistory> findAllByStatus(Character character);

	VpnHistory findByCodeAndStatus(String code, Character character);
}