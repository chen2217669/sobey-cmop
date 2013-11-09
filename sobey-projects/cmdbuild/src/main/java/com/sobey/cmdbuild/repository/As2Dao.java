package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.As2;

public interface As2Dao extends PagingAndSortingRepository<As2, Integer>, JpaSpecificationExecutor<As2> {

}