package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Dns;

public interface DnsDao extends PagingAndSortingRepository<Dns, Integer>, JpaSpecificationExecutor<Dns> {

}