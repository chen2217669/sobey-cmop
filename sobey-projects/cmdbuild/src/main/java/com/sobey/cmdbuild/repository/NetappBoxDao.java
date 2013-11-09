package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.NetappBox;

public interface NetappBoxDao extends PagingAndSortingRepository<NetappBox, Integer>,
		JpaSpecificationExecutor<NetappBox> {

}