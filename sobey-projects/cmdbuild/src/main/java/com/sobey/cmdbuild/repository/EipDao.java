package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Eip;

public interface EipDao extends PagingAndSortingRepository<Eip, Integer>, JpaSpecificationExecutor<Eip> {

	List<Eip> findAllByStatus(Character character);

	Eip findByCodeAndStatus(String code, Character character);
}