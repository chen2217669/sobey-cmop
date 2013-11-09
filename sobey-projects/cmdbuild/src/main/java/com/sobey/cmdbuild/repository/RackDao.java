package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Rack;

public interface RackDao extends PagingAndSortingRepository<Rack, Integer>, JpaSpecificationExecutor<Rack> {

}