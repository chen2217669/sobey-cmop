package com.sobey.cmdbuild.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.repository.CompanyDao;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

@Component
@Transactional
public class OrganisationService {

	@Autowired
	private CompanyDao companyDao;

	public Company findCompany(Integer id) {
		return companyDao.findOne(id);
	}

	public Company saveOrUpdate(Company company) {
		return companyDao.save(company);
	}

	public List<Company> getCompany() {
		return (List<Company>) companyDao.findAll();
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		return new PageRequest(pageNumber - 1, pagzSize, new Sort(Direction.DESC, "id"));
	}

	public Page<Company> getCompanyPageable(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<Company> spec = buildSpecification(searchParams);

		return companyDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Company> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Company> spec = DynamicSpecifications.bySearchFilter(filters.values(), Company.class);
		return spec;
	}

}
