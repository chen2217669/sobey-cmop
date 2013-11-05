package com.sobey.cmdbuild.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sobey.cmdbuild.entity.GroupPolicy;

public interface GroupPolicyDao extends PagingAndSortingRepository<GroupPolicy, Integer>,
		JpaSpecificationExecutor<GroupPolicy> {
	List<GroupPolicy> findAllByStatus(Character character);

	GroupPolicy findByCodeAndStatus(String code, Character character);
}