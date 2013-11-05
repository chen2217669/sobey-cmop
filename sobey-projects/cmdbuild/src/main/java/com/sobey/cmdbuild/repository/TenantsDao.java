package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.Tenants;

public interface TenantsDao extends PagingAndSortingRepository<Tenants, Integer>, JpaSpecificationExecutor<Tenants> {
	List<Tenants> findAllByStatus(Character character);

	Tenants findByCodeAndStatus(String code, Character character);
}