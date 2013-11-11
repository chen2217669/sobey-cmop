package com.sobey.cmdbuild.service.iaas;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.As2;
import com.sobey.cmdbuild.repository.As2Dao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.As2DTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * As2的service类.
 */
@Service
@Transactional
public class As2Service extends BasicSevcie {
	@Autowired
	private As2Dao as2Dao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return As2
	 */
	public As2 findAs2(Integer id) {
		return as2Dao.findOne(id);
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
	 * @return As2
	 */
	public As2 findAs2(Map<String, Object> searchParams) {
		return as2Dao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param As2
	 * @return As2
	 */
	public As2 saveOrUpdate(As2 as2) {
		return as2Dao.save(as2);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteAs2(Integer id) {
		as2Dao.delete(id);
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
	 *            动态查询条件Map * @return List<As2>
	 */
	public List<As2> getAs2List(Map<String, Object> searchParams) {
		return as2Dao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<As2>
	 */
	private Page<As2> getAs2Page(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<As2> spec = buildSpecification(searchParams);

		return as2Dao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<As2>
	 */
	private Specification<As2> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), As2.class);
	}

	/**
	 * As2DTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<As2DTO>
	 */
	public PaginationResult<As2DTO> getAs2DTOPagination(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		Page<As2> page = getAs2Page(searchParams, pageNumber, pageSize);

		List<As2DTO> dtos = BeanMapper.mapList(page.getContent(), As2DTO.class);

		return fillPaginationResult(page, dtos);
	}
}