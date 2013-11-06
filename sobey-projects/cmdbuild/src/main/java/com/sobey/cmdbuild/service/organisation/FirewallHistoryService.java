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
import com.sobey.cmdbuild.entity.FirewallHistory;
import com.sobey.cmdbuild.repository.FirewallHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.FirewallHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * FirewallHistory的service类.
 */
@Service
@Transactional
public class FirewallHistoryService extends BasicSevcie {
	@Autowired
	private FirewallHistoryDao firewallHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return FirewallHistory
	 */
	public FirewallHistory findFirewallHistory(Integer id) {
		return firewallHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param firewallHistory
	 * @return FirewallHistory
	 */
	public FirewallHistory saveOrUpdate(FirewallHistory firewallHistory) {
		return firewallHistoryDao.save(firewallHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteFirewallHistory(Integer id) {
		firewallHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return FirewallHistory
	 */
	public FirewallHistory findByCode(String code) {
		return firewallHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<FirewallHistory>
	 */
	public List<FirewallHistory> getCompanies() {
		return firewallHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<FirewallHistory>
	 */
	private Page<FirewallHistory> getFirewallHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<FirewallHistory> spec = buildSpecification(searchParams);
		return firewallHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<FirewallHistory>
	 */
	private Specification<FirewallHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<FirewallHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				FirewallHistory.class);
		return spec;
	}

	/**
	 * FirewallHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<FirewallHistoryDTO>
	 */
	public PaginationResult<FirewallHistoryDTO> getFirewallHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<FirewallHistory> page = getFirewallHistoryPage(searchParams, pageNumber, pageSize); // 将List<FirewallHistory>中的数据转换为List<FirewallHistoryDTO>
		List<FirewallHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), FirewallHistoryDTO.class);
		PaginationResult<FirewallHistoryDTO> paginationResult = new PaginationResult<FirewallHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}