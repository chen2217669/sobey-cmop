package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.DeviceSpec;

public interface DeviceSpecDao extends PagingAndSortingRepository<DeviceSpec, Integer>,
		JpaSpecificationExecutor<DeviceSpec> {

	List<DeviceSpec> findAllByStatus(Character character);

	DeviceSpec findByCodeAndStatus(String code, Character character);
}