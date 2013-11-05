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
import com.sobey.cmdbuild.entity.Cs2History;
import com.sobey.cmdbuild.repository.Cs2HistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.Cs2HistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Cs2History的service类.
 */
@Service
@Transactional
public class Cs2HistoryService extends BasicSevcie {
	@Autowired
	private Cs2HistoryDao cs2HistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Cs2History
	 */
	public Cs2History findCs2History(Integer id) {
		return cs2HistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param cs2History
	 * @return Cs2History
	 */
	public Cs2History saveOrUpdate(Cs2History cs2History) {
		return cs2HistoryDao.save(cs2History);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteCs2History(Integer id) {
		cs2HistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return Cs2History
	 */
	public Cs2History findByCode(String code) {
		return cs2HistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<Cs2History>
	 */
	public List<Cs2History> getCompanies() {
		return cs2HistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Cs2History>
	 */
	private Page<Cs2History> getCs2HistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Cs2History> spec = buildSpecification(searchParams);
		return cs2HistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Cs2History>
	 */
	private Specification<Cs2History> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Cs2History> spec = DynamicSpecifications.bySearchFilter(filters.values(), Cs2History.class);
		return spec;
	}

	/**
	 * Cs2HistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<Cs2HistoryDTO>
	 */
	public PaginationResult<Cs2HistoryDTO> getCs2HistoryDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<Cs2History> page = getCs2HistoryPage(searchParams, pageNumber, pageSize); // 将List<Cs2History>中的数据转换为List<Cs2HistoryDTO>
		List<Cs2HistoryDTO> dtos = BeanMapper.mapList(page.getContent(), Cs2HistoryDTO.class);
		PaginationResult<Cs2HistoryDTO> paginationResult = new PaginationResult<Cs2HistoryDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}