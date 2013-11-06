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
import com.sobey.cmdbuild.entity.ElbHistory;
import com.sobey.cmdbuild.repository.ElbHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.ElbHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * ElbHistory的service类.
 */
@Service
@Transactional
public class ElbHistoryService extends BasicSevcie {
	@Autowired
	private ElbHistoryDao elbHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return ElbHistory
	 */
	public ElbHistory findElbHistory(Integer id) {
		return elbHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param elbHistory
	 * @return ElbHistory
	 */
	public ElbHistory saveOrUpdate(ElbHistory elbHistory) {
		return elbHistoryDao.save(elbHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteElbHistory(Integer id) {
		elbHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return ElbHistory
	 */
	public ElbHistory findByCode(String code) {
		return elbHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<ElbHistory>
	 */
	public List<ElbHistory> getCompanies() {
		return elbHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<ElbHistory>
	 */
	private Page<ElbHistory> getElbHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<ElbHistory> spec = buildSpecification(searchParams);
		return elbHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<ElbHistory>
	 */
	private Specification<ElbHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<ElbHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(), ElbHistory.class);
		return spec;
	}

	/**
	 * ElbHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<ElbHistoryDTO>
	 */
	public PaginationResult<ElbHistoryDTO> getElbHistoryDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<ElbHistory> page = getElbHistoryPage(searchParams, pageNumber, pageSize); // 将List<ElbHistory>中的数据转换为List<ElbHistoryDTO>
		List<ElbHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), ElbHistoryDTO.class);
		PaginationResult<ElbHistoryDTO> paginationResult = new PaginationResult<ElbHistoryDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}