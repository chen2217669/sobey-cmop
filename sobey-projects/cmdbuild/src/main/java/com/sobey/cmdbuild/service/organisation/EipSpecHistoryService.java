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
import com.sobey.cmdbuild.entity.EipSpecHistory;
import com.sobey.cmdbuild.repository.EipSpecHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * EipSpecHistory的service类.
 */
@Service
@Transactional
public class EipSpecHistoryService extends BasicSevcie {
	@Autowired
	private EipSpecHistoryDao eipSpecHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return EipSpecHistory
	 */
	public EipSpecHistory findEipSpecHistory(Integer id) {
		return eipSpecHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param eipSpecHistory
	 * @return EipSpecHistory
	 */
	public EipSpecHistory saveOrUpdate(EipSpecHistory eipSpecHistory) {
		return eipSpecHistoryDao.save(eipSpecHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEipSpecHistory(Integer id) {
		eipSpecHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return EipSpecHistory
	 */
	public EipSpecHistory findByCode(String code) {
		return eipSpecHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<EipSpecHistory>
	 */
	public List<EipSpecHistory> getCompanies() {
		return eipSpecHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<EipSpecHistory>
	 */
	private Page<EipSpecHistory> getEipSpecHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<EipSpecHistory> spec = buildSpecification(searchParams);
		return eipSpecHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<EipSpecHistory>
	 */
	private Specification<EipSpecHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<EipSpecHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				EipSpecHistory.class);
		return spec;
	}

	/**
	 * EipSpecHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<EipSpecHistoryDTO>
	 */
	public PaginationResult<EipSpecHistoryDTO> getEipSpecHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<EipSpecHistory> page = getEipSpecHistoryPage(searchParams, pageNumber, pageSize); // 将List<EipSpecHistory>中的数据转换为List<EipSpecHistoryDTO>
		List<EipSpecHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), EipSpecHistoryDTO.class);
		PaginationResult<EipSpecHistoryDTO> paginationResult = new PaginationResult<EipSpecHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}