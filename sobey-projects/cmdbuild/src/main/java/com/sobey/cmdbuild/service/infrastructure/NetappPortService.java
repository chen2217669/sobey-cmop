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
import com.sobey.cmdbuild.entity.NetappPort;
import com.sobey.cmdbuild.repository.NetappPortDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.NetappPortDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * NetappPort的service类.
 */
@Service
@Transactional
public class NetappPortService extends BasicSevcie {
	@Autowired
	private NetappPortDao netappPortDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return NetappPort
	 */
	public NetappPort findNetappPort(Integer id) {
		return netappPortDao.findOne(id);
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
	 * @return NetappPort
	 */
	public NetappPort findNetappPort(Map<String, Object> searchParams) {
		return netappPortDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param NetappPort
	 * @return NetappPort
	 */
	public NetappPort saveOrUpdate(NetappPort netappPort) {
		return netappPortDao.save(netappPort);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteNetappPort(Integer id) {
		netappPortDao.delete(id);
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
	 *            动态查询条件Map * @return List<NetappPort>
	 */
	public List<NetappPort> getNetappPortList(Map<String, Object> searchParams) {
		return netappPortDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<NetappPort>
	 */
	private Page<NetappPort> getNetappPortPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<NetappPort> spec = buildSpecification(searchParams);

		return netappPortDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<NetappPort>
	 */
	private Specification<NetappPort> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), NetappPort.class);
	}

	/**
	 * NetappPortDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<NetappPortDTO>
	 */
	public PaginationResult<NetappPortDTO> getNetappPortDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<NetappPort> page = getNetappPortPage(searchParams, pageNumber, pageSize);

		List<NetappPortDTO> dtos = BeanMapper.mapList(page.getContent(), NetappPortDTO.class);

		return fillPaginationResult(page, dtos);
	}
}