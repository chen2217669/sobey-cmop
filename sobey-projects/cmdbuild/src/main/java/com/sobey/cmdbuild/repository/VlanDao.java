package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Vlan;

public interface VlanDao extends PagingAndSortingRepository<Vlan, Integer>, JpaSpecificationExecutor<Vlan> {

}