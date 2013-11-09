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
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
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
	 * @return Company
	 */
	public Company findCompany(Integer id) {
		return companyDao.findOne(id);
	}

	/**
	 * 根据自定义动态查询条件获得对象.
	 * 
	 * 将条件查询放入searchParams中. 查询条件可查询{@link SearchFilter}类.
	 * 
	 * <pre>
	 * searchParams.put(&quot;EQ_status&quot;, 'A');
	 * </pre>
	 * 
	 * @param searchParams
	 *            动态查询条件Map
	 * @return
	 */
	public Company findCompany(Map<String, Object> searchParams) {
		return companyDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param company
	 * @return Company
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
	 * 根据自定义动态查询条件获得对象集合.
	 * 
	 * 将条件查询放入searchParams中. 查询条件可查询{@link SearchFilter}类.
	 * 
	 * <pre>
	 * searchParams.put(&quot;EQ_status&quot;, 'A');
	 * </pre>
	 * 
	 * @param searchParams
	 *            动态查询条件Map
	 * @return List<Company>
	 */
	public List<Company> getCompanyList(Map<String, Object> searchParams) {
		return companyDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Company>
	 */
	private Page<Company> getCompanyPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<Company> spec = buildSpecification(searchParams);

		return companyDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.默认获得状态为"A"的有效对象.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Tenants>
	 */
	private Specification<Company> buildSpecification(Map<String, Object> searchParams) {

		// 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), Company.class);
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
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<CompanyDTO>
	 */
	public PaginationResult<CompanyDTO> getCompanyDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<Company> page = getCompanyPage(searchParams, pageNumber, pageSize);

		// 将List<Company>中的数据转换为List<CompanyDTO>
		List<CompanyDTO> dtos = BeanMapper.mapList(page.getContent(), CompanyDTO.class);

		return fillPaginationResult(page, dtos);
	}

}
