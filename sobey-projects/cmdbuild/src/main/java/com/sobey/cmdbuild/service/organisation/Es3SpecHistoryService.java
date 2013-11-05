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
import com.sobey.cmdbuild.entity.Es3SpecHistory;
import com.sobey.cmdbuild.repository.Es3SpecHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.Es3SpecHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Es3SpecHistory的service类.
 */
@Service
@Transactional
public class Es3SpecHistoryService extends BasicSevcie {
	@Autowired
	private Es3SpecHistoryDao es3SpecHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Es3SpecHistory
	 */
	public Es3SpecHistory findEs3SpecHistory(Integer id) {
		return es3SpecHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param es3SpecHistory
	 * @return Es3SpecHistory
	 */
	public Es3SpecHistory saveOrUpdate(Es3SpecHistory es3SpecHistory) {
		return es3SpecHistoryDao.save(es3SpecHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEs3SpecHistory(Integer id) {
		es3SpecHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return Es3SpecHistory
	 */
	public Es3SpecHistory findByCode(String code) {
		return es3SpecHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<Es3SpecHistory>
	 */
	public List<Es3SpecHistory> getCompanies() {
		return es3SpecHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Es3SpecHistory>
	 */
	private Page<Es3SpecHistory> getEs3SpecHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Es3SpecHistory> spec = buildSpecification(searchParams);
		return es3SpecHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Es3SpecHistory>
	 */
	private Specification<Es3SpecHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Es3SpecHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				Es3SpecHistory.class);
		return spec;
	}

	/**
	 * Es3SpecHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<Es3SpecHistoryDTO>
	 */
	public PaginationResult<Es3SpecHistoryDTO> getEs3SpecHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<Es3SpecHistory> page = getEs3SpecHistoryPage(searchParams, pageNumber, pageSize); // 将List<Es3SpecHistory>中的数据转换为List<Es3SpecHistoryDTO>
		List<Es3SpecHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), Es3SpecHistoryDTO.class);
		PaginationResult<Es3SpecHistoryDTO> paginationResult = new PaginationResult<Es3SpecHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}