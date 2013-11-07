package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Tenants;

public interface TenantsDao extends PagingAndSortingRepository<Tenants, Integer>, JpaSpecificationExecutor<Tenants> {

}