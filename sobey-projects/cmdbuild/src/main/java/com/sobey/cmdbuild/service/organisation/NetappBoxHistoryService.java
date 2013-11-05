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
import com.sobey.cmdbuild.entity.NetappBoxHistory;
import com.sobey.cmdbuild.repository.NetappBoxHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.NetappBoxHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * NetappBoxHistory的service类.
 */
@Service
@Transactional
public class NetappBoxHistoryService extends BasicSevcie {
	@Autowired
	private NetappBoxHistoryDao netappBoxHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return NetappBoxHistory
	 */
	public NetappBoxHistory findNetappBoxHistory(Integer id) {
		return netappBoxHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param netappBoxHistory
	 * @return NetappBoxHistory
	 */
	public NetappBoxHistory saveOrUpdate(NetappBoxHistory netappBoxHistory) {
		return netappBoxHistoryDao.save(netappBoxHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteNetappBoxHistory(Integer id) {
		netappBoxHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return NetappBoxHistory
	 */
	public NetappBoxHistory findByCode(String code) {
		return netappBoxHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<NetappBoxHistory>
	 */
	public List<NetappBoxHistory> getCompanies() {
		return netappBoxHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<NetappBoxHistory>
	 */
	private Page<NetappBoxHistory> getNetappBoxHistoryPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<NetappBoxHistory> spec = buildSpecification(searchParams);
		return netappBoxHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<NetappBoxHistory>
	 */
	private Specification<NetappBoxHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<NetappBoxHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				NetappBoxHistory.class);
		return spec;
	}

	/**
	 * NetappBoxHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<NetappBoxHistoryDTO>
	 */
	public PaginationResult<NetappBoxHistoryDTO> getNetappBoxHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<NetappBoxHistory> page = getNetappBoxHistoryPage(searchParams, pageNumber, pageSize); // 将List<NetappBoxHistory>中的数据转换为List<NetappBoxHistoryDTO>
		List<NetappBoxHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), NetappBoxHistoryDTO.class);
		PaginationResult<NetappBoxHistoryDTO> paginationResult = new PaginationResult<NetappBoxHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}