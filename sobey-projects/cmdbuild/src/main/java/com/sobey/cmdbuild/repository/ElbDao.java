package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Elb;

public interface ElbDao extends PagingAndSortingRepository<Elb, Integer>, JpaSpecificationExecutor<Elb> {

}