package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Tag;

public interface TagDao extends PagingAndSortingRepository<Tag, Integer>, JpaSpecificationExecutor<Tag> {

}