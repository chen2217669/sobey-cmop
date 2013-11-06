package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.DeviceSpecHistory;

public interface DeviceSpecHistoryDao extends PagingAndSortingRepository<DeviceSpecHistory, Integer>,
		JpaSpecificationExecutor<DeviceSpecHistory> {
	List<DeviceSpecHistory> findAllByStatus(Character character);

	DeviceSpecHistory findByCodeAndStatus(String code, Character character);
}