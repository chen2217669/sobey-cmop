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
import com.sobey.cmdbuild.entity.LoadBalancerPort;
import com.sobey.cmdbuild.repository.LoadBalancerPortDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerPortDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * LoadBalancerPort的service类.
 */
@Service
@Transactional
public class LoadBalancerPortService extends BasicSevcie {
	@Autowired
	private LoadBalancerPortDao loadBalancerPortDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return LoadBalancerPort
	 */
	public LoadBalancerPort findLoadBalancerPort(Integer id) {
		return loadBalancerPortDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param loadBalancerPort
	 * @return LoadBalancerPort
	 */
	public LoadBalancerPort saveOrUpdate(LoadBalancerPort loadBalancerPort) {
		return loadBalancerPortDao.save(loadBalancerPort);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteLoadBalancerPort(Integer id) {
		loadBalancerPortDao.delete(id);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<LoadBalancerPort>
	 */
	private Page<LoadBalancerPort> getLoadBalancerPortPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<LoadBalancerPort> spec = buildSpecification(searchParams);
		return loadBalancerPortDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<LoadBalancerPort>
	 */
	private Specification<LoadBalancerPort> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<LoadBalancerPort> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				LoadBalancerPort.class);
		return spec;
	}

	/**
	 * LoadBalancerPortDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<LoadBalancerPortDTO>
	 */
	public PaginationResult<LoadBalancerPortDTO> getLoadBalancerPortDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<LoadBalancerPort> page = getLoadBalancerPortPage(searchParams, pageNumber, pageSize); // 将List<LoadBalancerPort>中的数据转换为List<LoadBalancerPortDTO>
		List<LoadBalancerPortDTO> dtos = BeanMapper.mapList(page.getContent(), LoadBalancerPortDTO.class);
		PaginationResult<LoadBalancerPortDTO> paginationResult = new PaginationResult<LoadBalancerPortDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}