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
import com.sobey.cmdbuild.entity.IdcHistory;
import com.sobey.cmdbuild.repository.IdcHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.IdcHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * IdcHistory的service类.
 */
@Service
@Transactional
public class IdcHistoryService extends BasicSevcie {
	@Autowired
	private IdcHistoryDao idcHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return IdcHistory
	 */
	public IdcHistory findIdcHistory(Integer id) {
		return idcHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param idcHistory
	 * @return IdcHistory
	 */
	public IdcHistory saveOrUpdate(IdcHistory idcHistory) {
		return idcHistoryDao.save(idcHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteIdcHistory(Integer id) {
		idcHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return IdcHistory
	 */
	public IdcHistory findByCode(String code) {
		return idcHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<IdcHistory>
	 */
	public List<IdcHistory> getCompanies() {
		return idcHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<IdcHistory>
	 */
	private Page<IdcHistory> getIdcHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<IdcHistory> spec = buildSpecification(searchParams);
		return idcHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<IdcHistory>
	 */
	private Specification<IdcHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<IdcHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(), IdcHistory.class);
		return spec;
	}

	/**
	 * IdcHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<IdcHistoryDTO>
	 */
	public PaginationResult<IdcHistoryDTO> getIdcHistoryDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<IdcHistory> page = getIdcHistoryPage(searchParams, pageNumber, pageSize); // 将List<IdcHistory>中的数据转换为List<IdcHistoryDTO>
		List<IdcHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), IdcHistoryDTO.class);
		PaginationResult<IdcHistoryDTO> paginationResult = new PaginationResult<IdcHistoryDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}