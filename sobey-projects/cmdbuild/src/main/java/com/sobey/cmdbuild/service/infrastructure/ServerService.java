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
import com.sobey.cmdbuild.entity.Server;
import com.sobey.cmdbuild.repository.ServerDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.ServerDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Server的service类.
 */
@Service
@Transactional
public class ServerService extends BasicSevcie {
	@Autowired
	private ServerDao serverDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Server
	 */
	public Server findServer(Integer id) {
		return serverDao.findOne(id);
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
	 * @return Server
	 */
	public Server findServer(Map<String, Object> searchParams) {
		return serverDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param Server
	 * @return Server
	 */
	public Server saveOrUpdate(Server server) {
		return serverDao.save(server);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteServer(Integer id) {
		serverDao.delete(id);
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
	 *            动态查询条件Map * @return List<Server>
	 */
	public List<Server> getServerList(Map<String, Object> searchParams) {
		return serverDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Server>
	 */
	private Page<Server> getServerPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<Server> spec = buildSpecification(searchParams);

		return serverDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<Server>
	 */
	private Specification<Server> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), Server.class);
	}

	/**
	 * ServerDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<ServerDTO>
	 */
	public PaginationResult<ServerDTO> getServerDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<Server> page = getServerPage(searchParams, pageNumber, pageSize);

		List<ServerDTO> dtos = BeanMapper.mapList(page.getContent(), ServerDTO.class);

		return fillPaginationResult(page, dtos);
	}
}