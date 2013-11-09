package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.EipSpec;

public interface EipSpecDao extends PagingAndSortingRepository<EipSpec, Integer>, JpaSpecificationExecutor<EipSpec> {

}