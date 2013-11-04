package com.sobey.cmdbuild.service.organisation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.repository.CompanyDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.base.PaginationResult;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Company的service类.
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional
public class CompanyService extends BasicSevcie {

	@Autowired
	private CompanyDao companyDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return
	 */
	public Company findCompany(Integer id) {
		return companyDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param company
	 * @return
	 */
	public Company saveOrUpdate(Company company) {
		return companyDao.save(company);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteCompany(Integer id) {
		companyDao.delete(id);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return
	 */
	public List<Company> getCompanies() {
		return companyDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	private Page<Company> getCompanyPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<Company> spec = buildSpecification(searchParams);

		return companyDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return
	 */
	private Specification<Company> buildSpecification(Map<String, Object> searchParams) {

		// 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		Specification<Company> spec = DynamicSpecifications.bySearchFilter(filters.values(), Company.class);

		return spec;
	}

	/**
	 * CompanyDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return
	 */
	public PaginationResult<CompanyDTO> getCompanyDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<Company> page = getCompanyPage(searchParams, pageNumber, pageSize);

		// 将List<Company>中的数据转换为List<CompanyDTO>
		List<CompanyDTO> dtos = BeanMapper.mapList(page.getContent(), CompanyDTO.class);

		PaginationResult<CompanyDTO> paginationResult = new PaginationResult<CompanyDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);

		return paginationResult;
	}

}
