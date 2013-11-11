package com.sobey.cmdbuild.service.infrastructure;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.FimasBox;
import com.sobey.cmdbuild.repository.FimasBoxDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.FimasBoxDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * FimasBox的service类.
 */
@Service
@Transactional
public class FimasBoxService extends BasicSevcie {
	@Autowired
	private FimasBoxDao fimasBoxDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return FimasBox
	 */
	public FimasBox findFimasBox(Integer id) {
		return fimasBoxDao.findOne(id);
	}

	/**
	 * 根据自定义动态查询条件获得对象.
	 * 
	 * 将条件查询放入searchParams中. 查询条件可查询{@link SearchFilter}类.
	 * 
	 * <pre>
	 * searchParams.put(&quot;EQ_status&quot;, 'A');
	 * </pre>
	 * 
	 * @param searchParams
	 *            动态查询条件Map
	 * @return FimasBox
	 */
	public FimasBox findFimasBox(Map<String, Object> searchParams) {
		return fimasBoxDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param FimasBox
	 * @return FimasBox
	 */
	public FimasBox saveOrUpdate(FimasBox fimasBox) {
		return fimasBoxDao.save(fimasBox);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteFimasBox(Integer id) {
		fimasBoxDao.delete(id);
	}

	/**
	 * 根据自定义动态查询条件获得对象集合.
	 * 
	 * 将条件查询放入searchParams中. 查询条件可查询{@link SearchFilter}类.
	 * 
	 * <pre>
	 * searchParams.put(&quot;EQ_status&quot;, 'A');
	 * </pre>
	 * 
	 * @param searchParams
	 *            动态查询条件Map * @return List<FimasBox>
	 */
	public List<FimasBox> getFimasBoxList(Map<String, Object> searchParams) {
		return fimasBoxDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<FimasBox>
	 */
	private Page<FimasBox> getFimasBoxPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<FimasBox> spec = buildSpecification(searchParams);

		return fimasBoxDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<FimasBox>
	 */
	private Specification<FimasBox> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), FimasBox.class);
	}

	/**
	 * FimasBoxDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<FimasBoxDTO>
	 */
	public PaginationResult<FimasBoxDTO> getFimasBoxDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<FimasBox> page = getFimasBoxPage(searchParams, pageNumber, pageSize);

		List<FimasBoxDTO> dtos = BeanMapper.mapList(page.getContent(), FimasBoxDTO.class);

		return fillPaginationResult(page, dtos);
	}
}