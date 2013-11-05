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
import com.sobey.cmdbuild.entity.ElbPolicyHistory;
import com.sobey.cmdbuild.repository.ElbPolicyHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.ElbPolicyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * ElbPolicyHistory的service类.
 */
@Service
@Transactional
public class ElbPolicyHistoryService extends BasicSevcie {
	@Autowired
	private ElbPolicyHistoryDao elbPolicyHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return ElbPolicyHistory
	 */
	public ElbPolicyHistory findElbPolicyHistory(Integer id) {
		return elbPolicyHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param elbPolicyHistory
	 * @return ElbPolicyHistory
	 */
	public ElbPolicyHistory saveOrUpdate(ElbPolicyHistory elbPolicyHistory) {
		return elbPolicyHistoryDao.save(elbPolicyHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteElbPolicyHistory(Integer id) {
		elbPolicyHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return ElbPolicyHistory
	 */
	public ElbPolicyHistory findByCode(String code) {
		return elbPolicyHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<ElbPolicyHistory>
	 */
	public List<ElbPolicyHistory> getCompanies() {
		return elbPolicyHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<ElbPolicyHistory>
	 */
	private Page<ElbPolicyHistory> getElbPolicyHistoryPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<ElbPolicyHistory> spec = buildSpecification(searchParams);
		return elbPolicyHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<ElbPolicyHistory>
	 */
	private Specification<ElbPolicyHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<ElbPolicyHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				ElbPolicyHistory.class);
		return spec;
	}

	/**
	 * ElbPolicyHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<ElbPolicyHistoryDTO>
	 */
	public PaginationResult<ElbPolicyHistoryDTO> getElbPolicyHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<ElbPolicyHistory> page = getElbPolicyHistoryPage(searchParams, pageNumber, pageSize); // 将List<ElbPolicyHistory>中的数据转换为List<ElbPolicyHistoryDTO>
		List<ElbPolicyHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), ElbPolicyHistoryDTO.class);
		PaginationResult<ElbPolicyHistoryDTO> paginationResult = new PaginationResult<ElbPolicyHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}