package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Vpn;

public interface VpnDao extends PagingAndSortingRepository<Vpn, Integer>, JpaSpecificationExecutor<Vpn> {

}