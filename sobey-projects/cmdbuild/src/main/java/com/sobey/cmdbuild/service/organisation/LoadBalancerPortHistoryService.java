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
import com.sobey.cmdbuild.entity.LoadBalancerPortHistory;
import com.sobey.cmdbuild.repository.LoadBalancerPortHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * LoadBalancerPortHistory的service类.
 */
@Service
@Transactional
public class LoadBalancerPortHistoryService extends BasicSevcie {
	@Autowired
	private LoadBalancerPortHistoryDao loadBalancerPortHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return LoadBalancerPortHistory
	 */
	public LoadBalancerPortHistory findLoadBalancerPortHistory(Integer id) {
		return loadBalancerPortHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param loadBalancerPortHistory
	 * @return LoadBalancerPortHistory
	 */
	public LoadBalancerPortHistory saveOrUpdate(LoadBalancerPortHistory loadBalancerPortHistory) {
		return loadBalancerPortHistoryDao.save(loadBalancerPortHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteLoadBalancerPortHistory(Integer id) {
		loadBalancerPortHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return LoadBalancerPortHistory
	 */
	public LoadBalancerPortHistory findByCode(String code) {
		return loadBalancerPortHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<LoadBalancerPortHistory>
	 */
	public List<LoadBalancerPortHistory> getCompanies() {
		return loadBalancerPortHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<LoadBalancerPortHistory>
	 */
	private Page<LoadBalancerPortHistory> getLoadBalancerPortHistoryPage(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<LoadBalancerPortHistory> spec = buildSpecification(searchParams);
		return loadBalancerPortHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<LoadBalancerPortHistory>
	 */
	private Specification<LoadBalancerPortHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<LoadBalancerPortHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				LoadBalancerPortHistory.class);
		return spec;
	}

	/**
	 * LoadBalancerPortHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<LoadBalancerPortHistoryDTO>
	 */
	public PaginationResult<LoadBalancerPortHistoryDTO> getLoadBalancerPortHistoryDTOPagination(
			Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<LoadBalancerPortHistory> page = getLoadBalancerPortHistoryPage(searchParams, pageNumber, pageSize); // 将List<LoadBalancerPortHistory>中的数据转换为List<LoadBalancerPortHistoryDTO>
		List<LoadBalancerPortHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), LoadBalancerPortHistoryDTO.class);
		PaginationResult<LoadBalancerPortHistoryDTO> paginationResult = new PaginationResult<LoadBalancerPortHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}