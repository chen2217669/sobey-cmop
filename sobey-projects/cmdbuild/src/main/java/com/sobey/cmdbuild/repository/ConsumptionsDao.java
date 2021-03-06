package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Consumptions;

public interface ConsumptionsDao extends PagingAndSortingRepository<Consumptions, Integer>,
		JpaSpecificationExecutor<Consumptions> {

}