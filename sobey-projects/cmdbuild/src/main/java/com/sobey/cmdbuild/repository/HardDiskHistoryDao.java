package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.HardDiskHistory;

public interface HardDiskHistoryDao extends PagingAndSortingRepository<HardDiskHistory, Integer>,
		JpaSpecificationExecutor<HardDiskHistory> {

	List<HardDiskHistory> findAllByStatus(Character character);

	HardDiskHistory findByCodeAndStatus(String code, Character character);
}