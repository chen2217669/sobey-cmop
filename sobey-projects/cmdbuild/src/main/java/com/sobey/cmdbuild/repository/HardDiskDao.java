package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.HardDisk;

public interface HardDiskDao extends PagingAndSortingRepository<HardDisk, Integer>, JpaSpecificationExecutor<HardDisk> {

}