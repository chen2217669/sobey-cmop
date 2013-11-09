package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.NetappController;

public interface NetappControllerDao extends PagingAndSortingRepository<NetappController, Integer>,
		JpaSpecificationExecutor<NetappController> {

}