package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Cs2History;

public interface Cs2HistoryDao extends PagingAndSortingRepository<Cs2History, Integer>,
		JpaSpecificationExecutor<Cs2History> {

	List<Cs2History> findAllByStatus(Character character);

	Cs2History findByCodeAndStatus(String code, Character character);
}