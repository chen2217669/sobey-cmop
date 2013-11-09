package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Switches;

public interface SwitchesDao extends PagingAndSortingRepository<Switches, Integer>, JpaSpecificationExecutor<Switches> {

}