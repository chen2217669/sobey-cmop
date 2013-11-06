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
import com.sobey.cmdbuild.entity.EipPolicyHistory;
import com.sobey.cmdbuild.repository.EipPolicyHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.EipPolicyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * EipPolicyHistory的service类.
 */
@Service
@Transactional
public class EipPolicyHistoryService extends BasicSevcie {
	@Autowired
	private EipPolicyHistoryDao eipPolicyHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return EipPolicyHistory
	 */
	public EipPolicyHistory findEipPolicyHistory(Integer id) {
		return eipPolicyHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param eipPolicyHistory
	 * @return EipPolicyHistory
	 */
	public EipPolicyHistory saveOrUpdate(EipPolicyHistory eipPolicyHistory) {
		return eipPolicyHistoryDao.save(eipPolicyHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEipPolicyHistory(Integer id) {
		eipPolicyHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return EipPolicyHistory
	 */
	public EipPolicyHistory findByCode(String code) {
		return eipPolicyHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<EipPolicyHistory>
	 */
	public List<EipPolicyHistory> getCompanies() {
		return eipPolicyHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<EipPolicyHistory>
	 */
	private Page<EipPolicyHistory> getEipPolicyHistoryPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<EipPolicyHistory> spec = buildSpecification(searchParams);
		return eipPolicyHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<EipPolicyHistory>
	 */
	private Specification<EipPolicyHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<EipPolicyHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				EipPolicyHistory.class);
		return spec;
	}

	/**
	 * EipPolicyHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<EipPolicyHistoryDTO>
	 */
	public PaginationResult<EipPolicyHistoryDTO> getEipPolicyHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<EipPolicyHistory> page = getEipPolicyHistoryPage(searchParams, pageNumber, pageSize); // 将List<EipPolicyHistory>中的数据转换为List<EipPolicyHistoryDTO>
		List<EipPolicyHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), EipPolicyHistoryDTO.class);
		PaginationResult<EipPolicyHistoryDTO> paginationResult = new PaginationResult<EipPolicyHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}