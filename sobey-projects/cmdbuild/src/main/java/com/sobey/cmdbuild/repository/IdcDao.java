package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.Idc;

public interface IdcDao extends PagingAndSortingRepository<Idc, Integer>, JpaSpecificationExecutor<Idc> {
	List<Idc> findAllByStatus(Character character);

	Idc findByCodeAndStatus(String code, Character character);
}