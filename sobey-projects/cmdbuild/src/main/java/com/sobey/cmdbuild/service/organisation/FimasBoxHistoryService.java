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
import com.sobey.cmdbuild.entity.FimasBoxHistory;
import com.sobey.cmdbuild.repository.FimasBoxHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.FimasBoxHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * FimasBoxHistory的service类.
 */
@Service
@Transactional
public class FimasBoxHistoryService extends BasicSevcie {
	@Autowired
	private FimasBoxHistoryDao fimasBoxHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return FimasBoxHistory
	 */
	public FimasBoxHistory findFimasBoxHistory(Integer id) {
		return fimasBoxHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param fimasBoxHistory
	 * @return FimasBoxHistory
	 */
	public FimasBoxHistory saveOrUpdate(FimasBoxHistory fimasBoxHistory) {
		return fimasBoxHistoryDao.save(fimasBoxHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteFimasBoxHistory(Integer id) {
		fimasBoxHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return FimasBoxHistory
	 */
	public FimasBoxHistory findByCode(String code) {
		return fimasBoxHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<FimasBoxHistory>
	 */
	public List<FimasBoxHistory> getCompanies() {
		return fimasBoxHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<FimasBoxHistory>
	 */
	private Page<FimasBoxHistory> getFimasBoxHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<FimasBoxHistory> spec = buildSpecification(searchParams);
		return fimasBoxHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<FimasBoxHistory>
	 */
	private Specification<FimasBoxHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<FimasBoxHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				FimasBoxHistory.class);
		return spec;
	}

	/**
	 * FimasBoxHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<FimasBoxHistoryDTO>
	 */
	public PaginationResult<FimasBoxHistoryDTO> getFimasBoxHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<FimasBoxHistory> page = getFimasBoxHistoryPage(searchParams, pageNumber, pageSize); // 将List<FimasBoxHistory>中的数据转换为List<FimasBoxHistoryDTO>
		List<FimasBoxHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), FimasBoxHistoryDTO.class);
		PaginationResult<FimasBoxHistoryDTO> paginationResult = new PaginationResult<FimasBoxHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}