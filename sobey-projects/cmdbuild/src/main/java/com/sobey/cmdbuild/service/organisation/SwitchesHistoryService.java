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
import com.sobey.cmdbuild.entity.SwitchesHistory;
import com.sobey.cmdbuild.repository.SwitchesHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.SwitchesHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * SwitchesHistory的service类.
 */
@Service
@Transactional
public class SwitchesHistoryService extends BasicSevcie {
	@Autowired
	private SwitchesHistoryDao switchesHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return SwitchesHistory
	 */
	public SwitchesHistory findSwitchesHistory(Integer id) {
		return switchesHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param switchesHistory
	 * @return SwitchesHistory
	 */
	public SwitchesHistory saveOrUpdate(SwitchesHistory switchesHistory) {
		return switchesHistoryDao.save(switchesHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteSwitchesHistory(Integer id) {
		switchesHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return SwitchesHistory
	 */
	public SwitchesHistory findByCode(String code) {
		return switchesHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<SwitchesHistory>
	 */
	public List<SwitchesHistory> getCompanies() {
		return switchesHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<SwitchesHistory>
	 */
	private Page<SwitchesHistory> getSwitchesHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<SwitchesHistory> spec = buildSpecification(searchParams);
		return switchesHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<SwitchesHistory>
	 */
	private Specification<SwitchesHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<SwitchesHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				SwitchesHistory.class);
		return spec;
	}

	/**
	 * SwitchesHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<SwitchesHistoryDTO>
	 */
	public PaginationResult<SwitchesHistoryDTO> getSwitchesHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<SwitchesHistory> page = getSwitchesHistoryPage(searchParams, pageNumber, pageSize); // 将List<SwitchesHistory>中的数据转换为List<SwitchesHistoryDTO>
		List<SwitchesHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), SwitchesHistoryDTO.class);
		PaginationResult<SwitchesHistoryDTO> paginationResult = new PaginationResult<SwitchesHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}