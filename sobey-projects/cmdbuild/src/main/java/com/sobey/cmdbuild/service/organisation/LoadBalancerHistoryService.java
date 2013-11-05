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
import com.sobey.cmdbuild.entity.LoadBalancerHistory;
import com.sobey.cmdbuild.repository.LoadBalancerHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * LoadBalancerHistory的service类.
 */
@Service
@Transactional
public class LoadBalancerHistoryService extends BasicSevcie {
	@Autowired
	private LoadBalancerHistoryDao loadBalancerHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return LoadBalancerHistory
	 */
	public LoadBalancerHistory findLoadBalancerHistory(Integer id) {
		return loadBalancerHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param loadBalancerHistory
	 * @return LoadBalancerHistory
	 */
	public LoadBalancerHistory saveOrUpdate(LoadBalancerHistory loadBalancerHistory) {
		return loadBalancerHistoryDao.save(loadBalancerHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteLoadBalancerHistory(Integer id) {
		loadBalancerHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return LoadBalancerHistory
	 */
	public LoadBalancerHistory findByCode(String code) {
		return loadBalancerHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<LoadBalancerHistory>
	 */
	public List<LoadBalancerHistory> getCompanies() {
		return loadBalancerHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<LoadBalancerHistory>
	 */
	private Page<LoadBalancerHistory> getLoadBalancerHistoryPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<LoadBalancerHistory> spec = buildSpecification(searchParams);
		return loadBalancerHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<LoadBalancerHistory>
	 */
	private Specification<LoadBalancerHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<LoadBalancerHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				LoadBalancerHistory.class);
		return spec;
	}

	/**
	 * LoadBalancerHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<LoadBalancerHistoryDTO>
	 */
	public PaginationResult<LoadBalancerHistoryDTO> getLoadBalancerHistoryDTOPagination(
			Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<LoadBalancerHistory> page = getLoadBalancerHistoryPage(searchParams, pageNumber, pageSize); // 将List<LoadBalancerHistory>中的数据转换为List<LoadBalancerHistoryDTO>
		List<LoadBalancerHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), LoadBalancerHistoryDTO.class);
		PaginationResult<LoadBalancerHistoryDTO> paginationResult = new PaginationResult<LoadBalancerHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}