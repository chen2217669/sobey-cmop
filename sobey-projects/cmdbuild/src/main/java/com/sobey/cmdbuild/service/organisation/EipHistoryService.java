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
import com.sobey.cmdbuild.entity.EipHistory;
import com.sobey.cmdbuild.repository.EipHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.EipHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * EipHistory的service类.
 */
@Service
@Transactional
public class EipHistoryService extends BasicSevcie {
	@Autowired
	private EipHistoryDao eipHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return EipHistory
	 */
	public EipHistory findEipHistory(Integer id) {
		return eipHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param eipHistory
	 * @return EipHistory
	 */
	public EipHistory saveOrUpdate(EipHistory eipHistory) {
		return eipHistoryDao.save(eipHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEipHistory(Integer id) {
		eipHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return EipHistory
	 */
	public EipHistory findByCode(String code) {
		return eipHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<EipHistory>
	 */
	public List<EipHistory> getCompanies() {
		return eipHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<EipHistory>
	 */
	private Page<EipHistory> getEipHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<EipHistory> spec = buildSpecification(searchParams);
		return eipHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<EipHistory>
	 */
	private Specification<EipHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<EipHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(), EipHistory.class);
		return spec;
	}

	/**
	 * EipHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<EipHistoryDTO>
	 */
	public PaginationResult<EipHistoryDTO> getEipHistoryDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<EipHistory> page = getEipHistoryPage(searchParams, pageNumber, pageSize); // 将List<EipHistory>中的数据转换为List<EipHistoryDTO>
		List<EipHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), EipHistoryDTO.class);
		PaginationResult<EipHistoryDTO> paginationResult = new PaginationResult<EipHistoryDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}