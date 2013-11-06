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
import com.sobey.cmdbuild.entity.As2History;
import com.sobey.cmdbuild.repository.As2HistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.As2HistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * As2History的service类.
 */
@Service
@Transactional
public class As2HistoryService extends BasicSevcie {
	@Autowired
	private As2HistoryDao as2HistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return As2History
	 */
	public As2History findAs2History(Integer id) {
		return as2HistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param as2History
	 * @return As2History
	 */
	public As2History saveOrUpdate(As2History as2History) {
		return as2HistoryDao.save(as2History);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteAs2History(Integer id) {
		as2HistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return As2History
	 */
	public As2History findByCode(String code) {
		return as2HistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<As2History>
	 */
	public List<As2History> getCompanies() {
		return as2HistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<As2History>
	 */
	private Page<As2History> getAs2HistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<As2History> spec = buildSpecification(searchParams);
		return as2HistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<As2History>
	 */
	private Specification<As2History> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<As2History> spec = DynamicSpecifications.bySearchFilter(filters.values(), As2History.class);
		return spec;
	}

	/**
	 * As2HistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<As2HistoryDTO>
	 */
	public PaginationResult<As2HistoryDTO> getAs2HistoryDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<As2History> page = getAs2HistoryPage(searchParams, pageNumber, pageSize); // 将List<As2History>中的数据转换为List<As2HistoryDTO>
		List<As2HistoryDTO> dtos = BeanMapper.mapList(page.getContent(), As2HistoryDTO.class);
		PaginationResult<As2HistoryDTO> paginationResult = new PaginationResult<As2HistoryDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}