package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Firewall;

public interface FirewallDao extends PagingAndSortingRepository<Firewall, Integer>, JpaSpecificationExecutor<Firewall> {

}