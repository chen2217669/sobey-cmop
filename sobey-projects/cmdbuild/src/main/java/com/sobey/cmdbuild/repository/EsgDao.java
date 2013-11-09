package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Esg;

public interface EsgDao extends PagingAndSortingRepository<Esg, Integer>, JpaSpecificationExecutor<Esg> {

}