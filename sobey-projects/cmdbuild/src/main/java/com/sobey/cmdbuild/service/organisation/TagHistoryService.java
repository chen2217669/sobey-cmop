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
import com.sobey.cmdbuild.entity.TagHistory;
import com.sobey.cmdbuild.repository.TagHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.TagHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * TagHistory的service类.
 */
@Service
@Transactional
public class TagHistoryService extends BasicSevcie {
	@Autowired
	private TagHistoryDao tagHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return TagHistory
	 */
	public TagHistory findTagHistory(Integer id) {
		return tagHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param tagHistory
	 * @return TagHistory
	 */
	public TagHistory saveOrUpdate(TagHistory tagHistory) {
		return tagHistoryDao.save(tagHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteTagHistory(Integer id) {
		tagHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return TagHistory
	 */
	public TagHistory findByCode(String code) {
		return tagHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<TagHistory>
	 */
	public List<TagHistory> getCompanies() {
		return tagHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<TagHistory>
	 */
	private Page<TagHistory> getTagHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<TagHistory> spec = buildSpecification(searchParams);
		return tagHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<TagHistory>
	 */
	private Specification<TagHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<TagHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(), TagHistory.class);
		return spec;
	}

	/**
	 * TagHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<TagHistoryDTO>
	 */
	public PaginationResult<TagHistoryDTO> getTagHistoryDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<TagHistory> page = getTagHistoryPage(searchParams, pageNumber, pageSize); // 将List<TagHistory>中的数据转换为List<TagHistoryDTO>
		List<TagHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), TagHistoryDTO.class);
		PaginationResult<TagHistoryDTO> paginationResult = new PaginationResult<TagHistoryDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}