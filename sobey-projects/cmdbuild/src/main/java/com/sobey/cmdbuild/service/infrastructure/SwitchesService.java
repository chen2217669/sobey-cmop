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
import com.sobey.cmdbuild.entity.Switches;
import com.sobey.cmdbuild.repository.SwitchesDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.SwitchesDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Switches的service类.
 */
@Service
@Transactional
public class SwitchesService extends BasicSevcie {
	@Autowired
	private SwitchesDao switchesDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Switches
	 */
	public Switches findSwitches(Integer id) {
		return switchesDao.findOne(id);
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
	 * @return Switches
	 */
	public Switches findSwitches(Map<String, Object> searchParams) {
		return switchesDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param Switches
	 * @return Switches
	 */
	public Switches saveOrUpdate(Switches switches) {
		return switchesDao.save(switches);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteSwitches(Integer id) {
		switchesDao.delete(id);
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
	 *            动态查询条件Map * @return List<Switches>
	 */
	public List<Switches> getSwitchesList(Map<String, Object> searchParams) {
		return switchesDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Switches>
	 */
	private Page<Switches> getSwitchesPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<Switches> spec = buildSpecification(searchParams);

		return switchesDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<Switches>
	 */
	private Specification<Switches> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), Switches.class);
	}

	/**
	 * SwitchesDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<SwitchesDTO>
	 */
	public PaginationResult<SwitchesDTO> getSwitchesDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<Switches> page = getSwitchesPage(searchParams, pageNumber, pageSize);

		List<SwitchesDTO> dtos = BeanMapper.mapList(page.getContent(), SwitchesDTO.class);

		return fillPaginationResult(page, dtos);
	}
}