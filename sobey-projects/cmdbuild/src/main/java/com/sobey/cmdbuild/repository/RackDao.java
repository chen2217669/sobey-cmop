package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Rack;

public interface RackDao extends PagingAndSortingRepository<Rack, Integer>, JpaSpecificationExecutor<Rack> {

	List<Rack> findAllByStatus(Character character);

	Rack findByCodeAndStatus(String code, Character character);
}