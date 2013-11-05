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
import com.sobey.cmdbuild.entity.FimasPortHistory;
import com.sobey.cmdbuild.repository.FimasPortHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.FimasPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * FimasPortHistory的service类.
 */
@Service
@Transactional
public class FimasPortHistoryService extends BasicSevcie {
	@Autowired
	private FimasPortHistoryDao fimasPortHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return FimasPortHistory
	 */
	public FimasPortHistory findFimasPortHistory(Integer id) {
		return fimasPortHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param fimasPortHistory
	 * @return FimasPortHistory
	 */
	public FimasPortHistory saveOrUpdate(FimasPortHistory fimasPortHistory) {
		return fimasPortHistoryDao.save(fimasPortHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteFimasPortHistory(Integer id) {
		fimasPortHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return FimasPortHistory
	 */
	public FimasPortHistory findByCode(String code) {
		return fimasPortHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<FimasPortHistory>
	 */
	public List<FimasPortHistory> getCompanies() {
		return fimasPortHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<FimasPortHistory>
	 */
	private Page<FimasPortHistory> getFimasPortHistoryPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<FimasPortHistory> spec = buildSpecification(searchParams);
		return fimasPortHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<FimasPortHistory>
	 */
	private Specification<FimasPortHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<FimasPortHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				FimasPortHistory.class);
		return spec;
	}

	/**
	 * FimasPortHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<FimasPortHistoryDTO>
	 */
	public PaginationResult<FimasPortHistoryDTO> getFimasPortHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<FimasPortHistory> page = getFimasPortHistoryPage(searchParams, pageNumber, pageSize); // 将List<FimasPortHistory>中的数据转换为List<FimasPortHistoryDTO>
		List<FimasPortHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), FimasPortHistoryDTO.class);
		PaginationResult<FimasPortHistoryDTO> paginationResult = new PaginationResult<FimasPortHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}