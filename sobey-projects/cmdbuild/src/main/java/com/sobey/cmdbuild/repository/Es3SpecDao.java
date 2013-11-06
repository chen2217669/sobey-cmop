package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Es3Spec;

public interface Es3SpecDao extends PagingAndSortingRepository<Es3Spec, Integer>, JpaSpecificationExecutor<Es3Spec> {

	List<Es3Spec> findAllByStatus(Character character);

	Es3Spec findByCodeAndStatus(String code, Character character);
}