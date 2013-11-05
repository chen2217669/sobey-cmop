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
import com.sobey.cmdbuild.entity.FirewallPort;
import com.sobey.cmdbuild.repository.FirewallPortDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * FirewallPort的service类.
 */
@Service
@Transactional
public class FirewallPortService extends BasicSevcie {
	@Autowired
	private FirewallPortDao firewallPortDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return FirewallPort
	 */
	public FirewallPort findFirewallPort(Integer id) {
		return firewallPortDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param firewallPort
	 * @return FirewallPort
	 */
	public FirewallPort saveOrUpdate(FirewallPort firewallPort) {
		return firewallPortDao.save(firewallPort);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteFirewallPort(Integer id) {
		firewallPortDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return FirewallPort
	 */
	public FirewallPort findByCode(String code) {
		return firewallPortDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<FirewallPort>
	 */
	public List<FirewallPort> getCompanies() {
		return firewallPortDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<FirewallPort>
	 */
	private Page<FirewallPort> getFirewallPortPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<FirewallPort> spec = buildSpecification(searchParams);
		return firewallPortDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<FirewallPort>
	 */
	private Specification<FirewallPort> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<FirewallPort> spec = DynamicSpecifications.bySearchFilter(filters.values(), FirewallPort.class);
		return spec;
	}

	/**
	 * FirewallPortDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<FirewallPortDTO>
	 */
	public PaginationResult<FirewallPortDTO> getFirewallPortDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<FirewallPort> page = getFirewallPortPage(searchParams, pageNumber, pageSize); // 将List<FirewallPort>中的数据转换为List<FirewallPortDTO>
		List<FirewallPortDTO> dtos = BeanMapper.mapList(page.getContent(), FirewallPortDTO.class);
		PaginationResult<FirewallPortDTO> paginationResult = new PaginationResult<FirewallPortDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}