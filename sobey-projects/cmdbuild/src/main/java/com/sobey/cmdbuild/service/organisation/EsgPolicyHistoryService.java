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
import com.sobey.cmdbuild.entity.EsgPolicyHistory;
import com.sobey.cmdbuild.repository.EsgPolicyHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.EsgPolicyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * EsgPolicyHistory的service类.
 */
@Service
@Transactional
public class EsgPolicyHistoryService extends BasicSevcie {
	@Autowired
	private EsgPolicyHistoryDao esgPolicyHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return EsgPolicyHistory
	 */
	public EsgPolicyHistory findEsgPolicyHistory(Integer id) {
		return esgPolicyHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param esgPolicyHistory
	 * @return EsgPolicyHistory
	 */
	public EsgPolicyHistory saveOrUpdate(EsgPolicyHistory esgPolicyHistory) {
		return esgPolicyHistoryDao.save(esgPolicyHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEsgPolicyHistory(Integer id) {
		esgPolicyHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return EsgPolicyHistory
	 */
	public EsgPolicyHistory findByCode(String code) {
		return esgPolicyHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<EsgPolicyHistory>
	 */
	public List<EsgPolicyHistory> getCompanies() {
		return esgPolicyHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<EsgPolicyHistory>
	 */
	private Page<EsgPolicyHistory> getEsgPolicyHistoryPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<EsgPolicyHistory> spec = buildSpecification(searchParams);
		return esgPolicyHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<EsgPolicyHistory>
	 */
	private Specification<EsgPolicyHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<EsgPolicyHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				EsgPolicyHistory.class);
		return spec;
	}

	/**
	 * EsgPolicyHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<EsgPolicyHistoryDTO>
	 */
	public PaginationResult<EsgPolicyHistoryDTO> getEsgPolicyHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<EsgPolicyHistory> page = getEsgPolicyHistoryPage(searchParams, pageNumber, pageSize); // 将List<EsgPolicyHistory>中的数据转换为List<EsgPolicyHistoryDTO>
		List<EsgPolicyHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), EsgPolicyHistoryDTO.class);
		PaginationResult<EsgPolicyHistoryDTO> paginationResult = new PaginationResult<EsgPolicyHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}