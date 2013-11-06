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
import com.sobey.cmdbuild.entity.EcsSpecHistory;
import com.sobey.cmdbuild.repository.EcsSpecHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * EcsSpecHistory的service类.
 */
@Service
@Transactional
public class EcsSpecHistoryService extends BasicSevcie {
	@Autowired
	private EcsSpecHistoryDao ecsSpecHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return EcsSpecHistory
	 */
	public EcsSpecHistory findEcsSpecHistory(Integer id) {
		return ecsSpecHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param ecsSpecHistory
	 * @return EcsSpecHistory
	 */
	public EcsSpecHistory saveOrUpdate(EcsSpecHistory ecsSpecHistory) {
		return ecsSpecHistoryDao.save(ecsSpecHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEcsSpecHistory(Integer id) {
		ecsSpecHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return EcsSpecHistory
	 */
	public EcsSpecHistory findByCode(String code) {
		return ecsSpecHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<EcsSpecHistory>
	 */
	public List<EcsSpecHistory> getCompanies() {
		return ecsSpecHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<EcsSpecHistory>
	 */
	private Page<EcsSpecHistory> getEcsSpecHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<EcsSpecHistory> spec = buildSpecification(searchParams);
		return ecsSpecHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<EcsSpecHistory>
	 */
	private Specification<EcsSpecHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<EcsSpecHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				EcsSpecHistory.class);
		return spec;
	}

	/**
	 * EcsSpecHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<EcsSpecHistoryDTO>
	 */
	public PaginationResult<EcsSpecHistoryDTO> getEcsSpecHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<EcsSpecHistory> page = getEcsSpecHistoryPage(searchParams, pageNumber, pageSize); // 将List<EcsSpecHistory>中的数据转换为List<EcsSpecHistoryDTO>
		List<EcsSpecHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), EcsSpecHistoryDTO.class);
		PaginationResult<EcsSpecHistoryDTO> paginationResult = new PaginationResult<EcsSpecHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}