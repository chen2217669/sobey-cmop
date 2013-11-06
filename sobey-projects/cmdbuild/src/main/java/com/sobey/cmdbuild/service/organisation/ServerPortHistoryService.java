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
import com.sobey.cmdbuild.entity.ServerPortHistory;
import com.sobey.cmdbuild.repository.ServerPortHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * ServerPortHistory的service类.
 */
@Service
@Transactional
public class ServerPortHistoryService extends BasicSevcie {
	@Autowired
	private ServerPortHistoryDao serverPortHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return ServerPortHistory
	 */
	public ServerPortHistory findServerPortHistory(Integer id) {
		return serverPortHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param serverPortHistory
	 * @return ServerPortHistory
	 */
	public ServerPortHistory saveOrUpdate(ServerPortHistory serverPortHistory) {
		return serverPortHistoryDao.save(serverPortHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteServerPortHistory(Integer id) {
		serverPortHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return ServerPortHistory
	 */
	public ServerPortHistory findByCode(String code) {
		return serverPortHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<ServerPortHistory>
	 */
	public List<ServerPortHistory> getCompanies() {
		return serverPortHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<ServerPortHistory>
	 */
	private Page<ServerPortHistory> getServerPortHistoryPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<ServerPortHistory> spec = buildSpecification(searchParams);
		return serverPortHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<ServerPortHistory>
	 */
	private Specification<ServerPortHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<ServerPortHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				ServerPortHistory.class);
		return spec;
	}

	/**
	 * ServerPortHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<ServerPortHistoryDTO>
	 */
	public PaginationResult<ServerPortHistoryDTO> getServerPortHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<ServerPortHistory> page = getServerPortHistoryPage(searchParams, pageNumber, pageSize); // 将List<ServerPortHistory>中的数据转换为List<ServerPortHistoryDTO>
		List<ServerPortHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), ServerPortHistoryDTO.class);
		PaginationResult<ServerPortHistoryDTO> paginationResult = new PaginationResult<ServerPortHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}