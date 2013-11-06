package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Vlan;

public interface VlanDao extends PagingAndSortingRepository<Vlan, Integer>, JpaSpecificationExecutor<Vlan> {

	List<Vlan> findAllByStatus(Character character);

	Vlan findByCodeAndStatus(String code, Character character);
}