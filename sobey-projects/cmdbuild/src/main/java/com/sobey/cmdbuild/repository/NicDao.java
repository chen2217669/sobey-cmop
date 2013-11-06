package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Nic;

public interface NicDao extends PagingAndSortingRepository<Nic, Integer>, JpaSpecificationExecutor<Nic> {

	List<Nic> findAllByStatus(Character character);

	Nic findByCodeAndStatus(String code, Character character);
}