package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.LookUp;

public interface LookUpDao extends PagingAndSortingRepository<LookUp, Integer>, JpaSpecificationExecutor<LookUp> {

}
