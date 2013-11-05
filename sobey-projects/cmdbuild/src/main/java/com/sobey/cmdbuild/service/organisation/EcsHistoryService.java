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
import com.sobey.cmdbuild.entity.EcsHistory;
import com.sobey.cmdbuild.repository.EcsHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.EcsHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * EcsHistory的service类.
 */
@Service
@Transactional
public class EcsHistoryService extends BasicSevcie {
	@Autowired
	private EcsHistoryDao ecsHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return EcsHistory
	 */
	public EcsHistory findEcsHistory(Integer id) {
		return ecsHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param ecsHistory
	 * @return EcsHistory
	 */
	public EcsHistory saveOrUpdate(EcsHistory ecsHistory) {
		return ecsHistoryDao.save(ecsHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEcsHistory(Integer id) {
		ecsHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return EcsHistory
	 */
	public EcsHistory findByCode(String code) {
		return ecsHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<EcsHistory>
	 */
	public List<EcsHistory> getCompanies() {
		return ecsHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<EcsHistory>
	 */
	private Page<EcsHistory> getEcsHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<EcsHistory> spec = buildSpecification(searchParams);
		return ecsHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<EcsHistory>
	 */
	private Specification<EcsHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<EcsHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(), EcsHistory.class);
		return spec;
	}

	/**
	 * EcsHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<EcsHistoryDTO>
	 */
	public PaginationResult<EcsHistoryDTO> getEcsHistoryDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<EcsHistory> page = getEcsHistoryPage(searchParams, pageNumber, pageSize); // 将List<EcsHistory>中的数据转换为List<EcsHistoryDTO>
		List<EcsHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), EcsHistoryDTO.class);
		PaginationResult<EcsHistoryDTO> paginationResult = new PaginationResult<EcsHistoryDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}