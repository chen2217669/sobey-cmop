package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.NetappBox;

public interface NetappBoxDao extends PagingAndSortingRepository<NetappBox, Integer>,
		JpaSpecificationExecutor<NetappBox> {

	List<NetappBox> findAllByStatus(Character character);

	NetappBox findByCodeAndStatus(String code, Character character);
}