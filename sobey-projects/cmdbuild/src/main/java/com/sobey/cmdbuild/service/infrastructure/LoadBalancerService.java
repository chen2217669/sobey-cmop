package com.sobey.cmdbuild.service.infrastructure;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.LoadBalancer;
import com.sobey.cmdbuild.repository.LoadBalancerDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * LoadBalancer的service类.
 */
@Service
@Transactional
public class LoadBalancerService extends BasicSevcie {
	@Autowired
	private LoadBalancerDao loadBalancerDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return LoadBalancer
	 */
	public LoadBalancer findLoadBalancer(Integer id) {
		return loadBalancerDao.findOne(id);
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
	 * @return LoadBalancer
	 */
	public LoadBalancer findLoadBalancer(Map<String, Object> searchParams) {
		return loadBalancerDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param LoadBalancer
	 * @return LoadBalancer
	 */
	public LoadBalancer saveOrUpdate(LoadBalancer loadBalancer) {
		return loadBalancerDao.save(loadBalancer);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteLoadBalancer(Integer id) {
		loadBalancerDao.delete(id);
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
	 *            动态查询条件Map * @return List<LoadBalancer>
	 */
	public List<LoadBalancer> getLoadBalancerList(Map<String, Object> searchParams) {
		return loadBalancerDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<LoadBalancer>
	 */
	private Page<LoadBalancer> getLoadBalancerPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<LoadBalancer> spec = buildSpecification(searchParams);

		return loadBalancerDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<LoadBalancer>
	 */
	private Specification<LoadBalancer> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), LoadBalancer.class);
	}

	/**
	 * LoadBalancerDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<LoadBalancerDTO>
	 */
	public PaginationResult<LoadBalancerDTO> getLoadBalancerDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {

		Page<LoadBalancer> page = getLoadBalancerPage(searchParams, pageNumber, pageSize);

		List<LoadBalancerDTO> dtos = BeanMapper.mapList(page.getContent(), LoadBalancerDTO.class);

		return fillPaginationResult(page, dtos);
	}
}