package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Cs2;

public interface Cs2Dao extends PagingAndSortingRepository<Cs2, Integer>, JpaSpecificationExecutor<Cs2> {

}