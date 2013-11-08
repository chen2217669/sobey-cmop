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
import com.sobey.cmdbuild.entity.LookUp;
import com.sobey.cmdbuild.repository.LookUpDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.LookUpDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * LookUp的service类.
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional
public class LookUpService extends BasicSevcie {

	@Autowired
	private LookUpDao lookUpDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return LookUp
	 */
	public LookUp findLookUp(Integer id) {
		return lookUpDao.findOne(id);
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
	 * @return
	 */
	public LookUp findLookUp(Map<String, Object> searchParams) {
		return lookUpDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param LookUp
	 * @return LookUp
	 */
	public LookUp saveOrUpdate(LookUp LookUp) {
		return lookUpDao.save(LookUp);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteLookUp(Integer id) {
		lookUpDao.delete(id);
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
	 *            动态查询条件Map
	 * @return List<LookUp>
	 */
	public List<LookUp> getLookUpList(Map<String, Object> searchParams) {
		return lookUpDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<LookUp>
	 */
	private Page<LookUp> getLookUpPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<LookUp> spec = buildSpecification(searchParams);

		return lookUpDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.默认获得状态为"A"的有效对象.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Tenants>
	 */
	private Specification<LookUp> buildSpecification(Map<String, Object> searchParams) {

		// 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), LookUp.class);
	}

	/**
	 * LookUpDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<LookUpDTO>
	 */
	public PaginationResult<LookUpDTO> getLookUpDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<LookUp> page = getLookUpPage(searchParams, pageNumber, pageSize);

		// 将List<LookUp>中的数据转换为List<LookUpDTO>
		List<LookUpDTO> dtos = BeanMapper.mapList(page.getContent(), LookUpDTO.class);

		return fillPaginationResult(page, dtos);
	}

}
