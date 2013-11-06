package com.sobey.cmdbuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Tag;

public interface TagDao extends PagingAndSortingRepository<Tag, Integer>, JpaSpecificationExecutor<Tag> {

	List<Tag> findAllByStatus(Character character);

	Tag findByCodeAndStatus(String code, Character character);
}