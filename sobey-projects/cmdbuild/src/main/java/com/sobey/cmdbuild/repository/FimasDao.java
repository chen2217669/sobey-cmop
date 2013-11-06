package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Fimas;

public interface FimasDao extends PagingAndSortingRepository<Fimas, Integer>, JpaSpecificationExecutor<Fimas> {

	List<Fimas> findAllByStatus(Character character);

	Fimas findByCodeAndStatus(String code, Character character);
}