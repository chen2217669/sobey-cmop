package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.TagHistory;

public interface TagHistoryDao extends PagingAndSortingRepository<TagHistory, Integer>,
		JpaSpecificationExecutor<TagHistory> {

}