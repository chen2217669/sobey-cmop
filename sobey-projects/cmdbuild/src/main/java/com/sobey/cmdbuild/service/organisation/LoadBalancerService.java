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
	 * 新增、保存对象
	 * 
	 * @param loadBalancer
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
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return LoadBalancer
	 */
	public LoadBalancer findByCode(String code) {
		return loadBalancerDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<LoadBalancer>
	 */
	public List<LoadBalancer> getCompanies() {
		return loadBalancerDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
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
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<LoadBalancer>
	 */
	private Specification<LoadBalancer> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<LoadBalancer> spec = DynamicSpecifications.bySearchFilter(filters.values(), LoadBalancer.class);
		return spec;
	}

	/**
	 * LoadBalancerDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<LoadBalancerDTO>
	 */
	public PaginationResult<LoadBalancerDTO> getLoadBalancerDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<LoadBalancer> page = getLoadBalancerPage(searchParams, pageNumber, pageSize); // 将List<LoadBalancer>中的数据转换为List<LoadBalancerDTO>
		List<LoadBalancerDTO> dtos = BeanMapper.mapList(page.getContent(), LoadBalancerDTO.class);
		PaginationResult<LoadBalancerDTO> paginationResult = new PaginationResult<LoadBalancerDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}