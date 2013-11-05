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
import com.sobey.cmdbuild.entity.FirewallPortHistory;
import com.sobey.cmdbuild.repository.FirewallPortHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * FirewallPortHistory的service类.
 */
@Service
@Transactional
public class FirewallPortHistoryService extends BasicSevcie {
	@Autowired
	private FirewallPortHistoryDao firewallPortHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return FirewallPortHistory
	 */
	public FirewallPortHistory findFirewallPortHistory(Integer id) {
		return firewallPortHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param firewallPortHistory
	 * @return FirewallPortHistory
	 */
	public FirewallPortHistory saveOrUpdate(FirewallPortHistory firewallPortHistory) {
		return firewallPortHistoryDao.save(firewallPortHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteFirewallPortHistory(Integer id) {
		firewallPortHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return FirewallPortHistory
	 */
	public FirewallPortHistory findByCode(String code) {
		return firewallPortHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<FirewallPortHistory>
	 */
	public List<FirewallPortHistory> getCompanies() {
		return firewallPortHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<FirewallPortHistory>
	 */
	private Page<FirewallPortHistory> getFirewallPortHistoryPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<FirewallPortHistory> spec = buildSpecification(searchParams);
		return firewallPortHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<FirewallPortHistory>
	 */
	private Specification<FirewallPortHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<FirewallPortHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				FirewallPortHistory.class);
		return spec;
	}

	/**
	 * FirewallPortHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<FirewallPortHistoryDTO>
	 */
	public PaginationResult<FirewallPortHistoryDTO> getFirewallPortHistoryDTOPagination(
			Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<FirewallPortHistory> page = getFirewallPortHistoryPage(searchParams, pageNumber, pageSize); // 将List<FirewallPortHistory>中的数据转换为List<FirewallPortHistoryDTO>
		List<FirewallPortHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), FirewallPortHistoryDTO.class);
		PaginationResult<FirewallPortHistoryDTO> paginationResult = new PaginationResult<FirewallPortHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}