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
import com.sobey.cmdbuild.entity.ServerPort;
import com.sobey.cmdbuild.repository.ServerPortDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * ServerPort的service类.
 */
@Service
@Transactional
public class ServerPortService extends BasicSevcie {
	@Autowired
	private ServerPortDao serverPortDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return ServerPort
	 */
	public ServerPort findServerPort(Integer id) {
		return serverPortDao.findOne(id);
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
	 * @return ServerPort
	 */
	public ServerPort findServerPort(Map<String, Object> searchParams) {
		return serverPortDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param ServerPort
	 * @return ServerPort
	 */
	public ServerPort saveOrUpdate(ServerPort serverPort) {
		return serverPortDao.save(serverPort);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteServerPort(Integer id) {
		serverPortDao.delete(id);
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
	 *            动态查询条件Map * @return List<ServerPort>
	 */
	public List<ServerPort> getServerPortList(Map<String, Object> searchParams) {
		return serverPortDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<ServerPort>
	 */
	private Page<ServerPort> getServerPortPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<ServerPort> spec = buildSpecification(searchParams);

		return serverPortDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<ServerPort>
	 */
	private Specification<ServerPort> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), ServerPort.class);
	}

	/**
	 * ServerPortDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<ServerPortDTO>
	 */
	public PaginationResult<ServerPortDTO> getServerPortDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<ServerPort> page = getServerPortPage(searchParams, pageNumber, pageSize);

		List<ServerPortDTO> dtos = BeanMapper.mapList(page.getContent(), ServerPortDTO.class);

		return fillPaginationResult(page, dtos);
	}
}