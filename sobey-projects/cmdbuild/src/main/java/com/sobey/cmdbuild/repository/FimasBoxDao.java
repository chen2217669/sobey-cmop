package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.FimasBox;

public interface FimasBoxDao extends PagingAndSortingRepository<FimasBox, Integer>, JpaSpecificationExecutor<FimasBox> {

	List<FimasBox> findAllByStatus(Character character);

	FimasBox findByCodeAndStatus(String code, Character character);
}