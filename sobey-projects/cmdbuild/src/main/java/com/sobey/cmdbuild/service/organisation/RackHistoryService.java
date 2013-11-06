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
import com.sobey.cmdbuild.entity.RackHistory;
import com.sobey.cmdbuild.repository.RackHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.RackHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * RackHistory的service类.
 */
@Service
@Transactional
public class RackHistoryService extends BasicSevcie {
	@Autowired
	private RackHistoryDao rackHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return RackHistory
	 */
	public RackHistory findRackHistory(Integer id) {
		return rackHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param rackHistory
	 * @return RackHistory
	 */
	public RackHistory saveOrUpdate(RackHistory rackHistory) {
		return rackHistoryDao.save(rackHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteRackHistory(Integer id) {
		rackHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return RackHistory
	 */
	public RackHistory findByCode(String code) {
		return rackHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<RackHistory>
	 */
	public List<RackHistory> getCompanies() {
		return rackHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<RackHistory>
	 */
	private Page<RackHistory> getRackHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<RackHistory> spec = buildSpecification(searchParams);
		return rackHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<RackHistory>
	 */
	private Specification<RackHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<RackHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(), RackHistory.class);
		return spec;
	}

	/**
	 * RackHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<RackHistoryDTO>
	 */
	public PaginationResult<RackHistoryDTO> getRackHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<RackHistory> page = getRackHistoryPage(searchParams, pageNumber, pageSize); // 将List<RackHistory>中的数据转换为List<RackHistoryDTO>
		List<RackHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), RackHistoryDTO.class);
		PaginationResult<RackHistoryDTO> paginationResult = new PaginationResult<RackHistoryDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}