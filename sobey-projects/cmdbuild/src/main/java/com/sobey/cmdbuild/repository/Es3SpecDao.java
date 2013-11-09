package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Es3Spec;

public interface Es3SpecDao extends PagingAndSortingRepository<Es3Spec, Integer>, JpaSpecificationExecutor<Es3Spec> {

}