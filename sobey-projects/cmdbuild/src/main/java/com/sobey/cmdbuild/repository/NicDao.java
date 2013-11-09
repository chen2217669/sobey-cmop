package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Nic;

public interface NicDao extends PagingAndSortingRepository<Nic, Integer>, JpaSpecificationExecutor<Nic> {

}