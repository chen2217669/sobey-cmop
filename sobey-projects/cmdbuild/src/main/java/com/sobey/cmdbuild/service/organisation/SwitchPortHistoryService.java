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
import com.sobey.cmdbuild.entity.SwitchPortHistory;
import com.sobey.cmdbuild.repository.SwitchPortHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.SwitchPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * SwitchPortHistory的service类.
 */
@Service
@Transactional
public class SwitchPortHistoryService extends BasicSevcie {
	@Autowired
	private SwitchPortHistoryDao switchPortHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return SwitchPortHistory
	 */
	public SwitchPortHistory findSwitchPortHistory(Integer id) {
		return switchPortHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param switchPortHistory
	 * @return SwitchPortHistory
	 */
	public SwitchPortHistory saveOrUpdate(SwitchPortHistory switchPortHistory) {
		return switchPortHistoryDao.save(switchPortHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteSwitchPortHistory(Integer id) {
		switchPortHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return SwitchPortHistory
	 */
	public SwitchPortHistory findByCode(String code) {
		return switchPortHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<SwitchPortHistory>
	 */
	public List<SwitchPortHistory> getCompanies() {
		return switchPortHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<SwitchPortHistory>
	 */
	private Page<SwitchPortHistory> getSwitchPortHistoryPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<SwitchPortHistory> spec = buildSpecification(searchParams);
		return switchPortHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<SwitchPortHistory>
	 */
	private Specification<SwitchPortHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<SwitchPortHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				SwitchPortHistory.class);
		return spec;
	}

	/**
	 * SwitchPortHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<SwitchPortHistoryDTO>
	 */
	public PaginationResult<SwitchPortHistoryDTO> getSwitchPortHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<SwitchPortHistory> page = getSwitchPortHistoryPage(searchParams, pageNumber, pageSize); // 将List<SwitchPortHistory>中的数据转换为List<SwitchPortHistoryDTO>
		List<SwitchPortHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), SwitchPortHistoryDTO.class);
		PaginationResult<SwitchPortHistoryDTO> paginationResult = new PaginationResult<SwitchPortHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}