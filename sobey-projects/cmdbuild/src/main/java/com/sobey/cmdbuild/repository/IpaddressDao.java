package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Ipaddress;

public interface IpaddressDao extends PagingAndSortingRepository<Ipaddress, Integer>,
		JpaSpecificationExecutor<Ipaddress> {

	List<Ipaddress> findAllByStatus(Character character);

	Ipaddress findByCodeAndStatus(String code, Character character);
}