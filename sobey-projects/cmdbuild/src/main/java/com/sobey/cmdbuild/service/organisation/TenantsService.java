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
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.repository.TenantsDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.TenantsDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Tenants的service类.
 */
@Service
@Transactional
public class TenantsService extends BasicSevcie {
	
	@Autowired
	private TenantsDao tenantsDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Tenants
	 */
	public Tenants findTenants(Integer id) {
		return tenantsDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param tenants
	 * @return Tenants
	 */
	public Tenants saveOrUpdate(Tenants tenants) {
		return tenantsDao.save(tenants);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteTenants(Integer id) {
		tenantsDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return Tenants
	 */
	public Tenants findByCode(String code) {
		return tenantsDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<Tenants>
	 */
	public List<Tenants> getTenants() {
		return tenantsDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Tenants>
	 */
	private Page<Tenants> getTenantsPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<Tenants> spec = buildSpecification(searchParams);

		return tenantsDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Tenants>
	 */
	private Specification<Tenants> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), Tenants.class);
	}

	/**
	 * TenantsDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<TenantsDTO>
	 */
	public PaginationResult<TenantsDTO> getTenantsDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<Tenants> page = getTenantsPage(searchParams, pageNumber, pageSize);

		List<TenantsDTO> dtos = BeanMapper.mapList(page.getContent(), TenantsDTO.class);

		return fillPaginationResult(page, dtos);
	}
}