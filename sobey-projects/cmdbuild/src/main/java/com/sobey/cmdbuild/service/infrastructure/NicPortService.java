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
import com.sobey.cmdbuild.entity.NicPort;
import com.sobey.cmdbuild.repository.NicPortDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.NicPortDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * NicPort的service类.
 */
@Service
@Transactional
public class NicPortService extends BasicSevcie {
	@Autowired
	private NicPortDao nicPortDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return NicPort
	 */
	public NicPort findNicPort(Integer id) {
		return nicPortDao.findOne(id);
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
	 * @return NicPort
	 */
	public NicPort findNicPort(Map<String, Object> searchParams) {
		return nicPortDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param NicPort
	 * @return NicPort
	 */
	public NicPort saveOrUpdate(NicPort nicPort) {
		return nicPortDao.save(nicPort);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteNicPort(Integer id) {
		nicPortDao.delete(id);
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
	 *            动态查询条件Map * @return List<NicPort>
	 */
	public List<NicPort> getNicPortList(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		return nicPortDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<NicPort>
	 */
	private Page<NicPort> getNicPortPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<NicPort> spec = buildSpecification(searchParams);

		return nicPortDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<NicPort>
	 */
	private Specification<NicPort> buildSpecification(Map<String, Object> searchParams) {

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), NicPort.class);
	}

	/**
	 * NicPortDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<NicPortDTO>
	 */
	public PaginationResult<NicPortDTO> getNicPortDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Page<NicPort> page = getNicPortPage(searchParams, pageNumber, pageSize);

		List<NicPortDTO> dtos = BeanMapper.mapList(page.getContent(), NicPortDTO.class);

		return fillPaginationResult(page, dtos);
	}
}