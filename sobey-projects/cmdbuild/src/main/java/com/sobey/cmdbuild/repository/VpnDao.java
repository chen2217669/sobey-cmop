package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.Vpn;

public interface VpnDao extends PagingAndSortingRepository<Vpn, Integer>, JpaSpecificationExecutor<Vpn> {
	List<Vpn> findAllByStatus(Character character);

	Vpn findByCodeAndStatus(String code, Character character);
}