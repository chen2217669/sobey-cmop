package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.NetappPort;

public interface NetappPortDao extends PagingAndSortingRepository<NetappPort, Integer>,
		JpaSpecificationExecutor<NetappPort> {

	List<NetappPort> findAllByStatus(Character character);

	NetappPort findByCodeAndStatus(String code, Character character);
}