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
import com.sobey.cmdbuild.entity.NicHistory;
import com.sobey.cmdbuild.repository.NicHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.NicHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * NicHistory的service类.
 */
@Service
@Transactional
public class NicHistoryService extends BasicSevcie {
	@Autowired
	private NicHistoryDao nicHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return NicHistory
	 */
	public NicHistory findNicHistory(Integer id) {
		return nicHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param nicHistory
	 * @return NicHistory
	 */
	public NicHistory saveOrUpdate(NicHistory nicHistory) {
		return nicHistoryDao.save(nicHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteNicHistory(Integer id) {
		nicHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return NicHistory
	 */
	public NicHistory findByCode(String code) {
		return nicHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<NicHistory>
	 */
	public List<NicHistory> getCompanies() {
		return nicHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<NicHistory>
	 */
	private Page<NicHistory> getNicHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<NicHistory> spec = buildSpecification(searchParams);
		return nicHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<NicHistory>
	 */
	private Specification<NicHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<NicHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(), NicHistory.class);
		return spec;
	}

	/**
	 * NicHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<NicHistoryDTO>
	 */
	public PaginationResult<NicHistoryDTO> getNicHistoryDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<NicHistory> page = getNicHistoryPage(searchParams, pageNumber, pageSize); // 将List<NicHistory>中的数据转换为List<NicHistoryDTO>
		List<NicHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), NicHistoryDTO.class);
		PaginationResult<NicHistoryDTO> paginationResult = new PaginationResult<NicHistoryDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}