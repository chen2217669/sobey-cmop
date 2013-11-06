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
import com.sobey.cmdbuild.entity.NetappControllerHistory;
import com.sobey.cmdbuild.repository.NetappControllerHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.NetappControllerHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * NetappControllerHistory的service类.
 */
@Service
@Transactional
public class NetappControllerHistoryService extends BasicSevcie {
	@Autowired
	private NetappControllerHistoryDao netappControllerHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return NetappControllerHistory
	 */
	public NetappControllerHistory findNetappControllerHistory(Integer id) {
		return netappControllerHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param netappControllerHistory
	 * @return NetappControllerHistory
	 */
	public NetappControllerHistory saveOrUpdate(NetappControllerHistory netappControllerHistory) {
		return netappControllerHistoryDao.save(netappControllerHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteNetappControllerHistory(Integer id) {
		netappControllerHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return NetappControllerHistory
	 */
	public NetappControllerHistory findByCode(String code) {
		return netappControllerHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<NetappControllerHistory>
	 */
	public List<NetappControllerHistory> getCompanies() {
		return netappControllerHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<NetappControllerHistory>
	 */
	private Page<NetappControllerHistory> getNetappControllerHistoryPage(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<NetappControllerHistory> spec = buildSpecification(searchParams);
		return netappControllerHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<NetappControllerHistory>
	 */
	private Specification<NetappControllerHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<NetappControllerHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				NetappControllerHistory.class);
		return spec;
	}

	/**
	 * NetappControllerHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<NetappControllerHistoryDTO>
	 */
	public PaginationResult<NetappControllerHistoryDTO> getNetappControllerHistoryDTOPagination(
			Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<NetappControllerHistory> page = getNetappControllerHistoryPage(searchParams, pageNumber, pageSize); // 将List<NetappControllerHistory>中的数据转换为List<NetappControllerHistoryDTO>
		List<NetappControllerHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), NetappControllerHistoryDTO.class);
		PaginationResult<NetappControllerHistoryDTO> paginationResult = new PaginationResult<NetappControllerHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}