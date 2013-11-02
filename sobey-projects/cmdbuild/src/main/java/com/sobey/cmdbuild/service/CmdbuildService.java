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
import com.sobey.cmdbuild.webservice.response.base.PaginationResult;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

@Component
@Transactional
public class CmdbuildService {

	@Autowired
	private CompanyDao companyDao;

	// ==== Company ====//

	public Company findCompany(Integer id) {
		return companyDao.findOne(id);
	}

	public Company saveOrUpdate(Company company) {
		return companyDao.save(company);
	}

	public List<Company> getCompany() {
		return (List<Company>) companyDao.findAll();
	}

	public void deleteCompany(Integer id) {
		companyDao.delete(id);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		return new PageRequest(pageNumber - 1, pagzSize, new Sort(Direction.DESC, "id"));
	}

	private Page<Company> getCompanyPageable(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<Company> spec = buildSpecification(searchParams);

		return companyDao.findAll(spec, pageRequest);
	}

	public PaginationResult<CompanyDTO> getCompanyDaoPageable(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<Company> page = getCompanyPageable(searchParams, pageNumber, pageSize);

		List<CompanyDTO> dtos = BeanMapper.mapList(page.getContent(), CompanyDTO.class);

		PaginationResult<CompanyDTO> paginationResult = new PaginationResult<CompanyDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);

		return paginationResult;

	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Company> buildSpecification(Map<String, Object> searchParams) {
		Character status = 'A';
		searchParams.put("EQ_status", status);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Company> spec = DynamicSpecifications.bySearchFilter(filters.values(), Company.class);
		return spec;
	}

}
