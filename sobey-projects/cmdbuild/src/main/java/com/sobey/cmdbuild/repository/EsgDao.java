package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Esg;

public interface EsgDao extends PagingAndSortingRepository<Esg, Integer>, JpaSpecificationExecutor<Esg> {

	List<Esg> findAllByStatus(Character character);

	Esg findByCodeAndStatus(String code, Character character);
}