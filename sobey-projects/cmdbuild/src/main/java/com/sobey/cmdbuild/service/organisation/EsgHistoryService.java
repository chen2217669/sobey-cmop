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
import com.sobey.cmdbuild.entity.EsgHistory;
import com.sobey.cmdbuild.repository.EsgHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.EsgHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * EsgHistory的service类.
 */
@Service
@Transactional
public class EsgHistoryService extends BasicSevcie {
	@Autowired
	private EsgHistoryDao esgHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return EsgHistory
	 */
	public EsgHistory findEsgHistory(Integer id) {
		return esgHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param esgHistory
	 * @return EsgHistory
	 */
	public EsgHistory saveOrUpdate(EsgHistory esgHistory) {
		return esgHistoryDao.save(esgHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEsgHistory(Integer id) {
		esgHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return EsgHistory
	 */
	public EsgHistory findByCode(String code) {
		return esgHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<EsgHistory>
	 */
	public List<EsgHistory> getCompanies() {
		return esgHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<EsgHistory>
	 */
	private Page<EsgHistory> getEsgHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<EsgHistory> spec = buildSpecification(searchParams);
		return esgHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<EsgHistory>
	 */
	private Specification<EsgHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<EsgHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(), EsgHistory.class);
		return spec;
	}

	/**
	 * EsgHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<EsgHistoryDTO>
	 */
	public PaginationResult<EsgHistoryDTO> getEsgHistoryDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<EsgHistory> page = getEsgHistoryPage(searchParams, pageNumber, pageSize); // 将List<EsgHistory>中的数据转换为List<EsgHistoryDTO>
		List<EsgHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), EsgHistoryDTO.class);
		PaginationResult<EsgHistoryDTO> paginationResult = new PaginationResult<EsgHistoryDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}