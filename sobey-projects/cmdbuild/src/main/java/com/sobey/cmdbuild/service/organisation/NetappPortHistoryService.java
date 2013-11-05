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
import com.sobey.cmdbuild.entity.NetappPortHistory;
import com.sobey.cmdbuild.repository.NetappPortHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.NetappPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * NetappPortHistory的service类.
 */
@Service
@Transactional
public class NetappPortHistoryService extends BasicSevcie {
	@Autowired
	private NetappPortHistoryDao netappPortHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return NetappPortHistory
	 */
	public NetappPortHistory findNetappPortHistory(Integer id) {
		return netappPortHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param netappPortHistory
	 * @return NetappPortHistory
	 */
	public NetappPortHistory saveOrUpdate(NetappPortHistory netappPortHistory) {
		return netappPortHistoryDao.save(netappPortHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteNetappPortHistory(Integer id) {
		netappPortHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return NetappPortHistory
	 */
	public NetappPortHistory findByCode(String code) {
		return netappPortHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<NetappPortHistory>
	 */
	public List<NetappPortHistory> getCompanies() {
		return netappPortHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<NetappPortHistory>
	 */
	private Page<NetappPortHistory> getNetappPortHistoryPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<NetappPortHistory> spec = buildSpecification(searchParams);
		return netappPortHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<NetappPortHistory>
	 */
	private Specification<NetappPortHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<NetappPortHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				NetappPortHistory.class);
		return spec;
	}

	/**
	 * NetappPortHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<NetappPortHistoryDTO>
	 */
	public PaginationResult<NetappPortHistoryDTO> getNetappPortHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<NetappPortHistory> page = getNetappPortHistoryPage(searchParams, pageNumber, pageSize); // 将List<NetappPortHistory>中的数据转换为List<NetappPortHistoryDTO>
		List<NetappPortHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), NetappPortHistoryDTO.class);
		PaginationResult<NetappPortHistoryDTO> paginationResult = new PaginationResult<NetappPortHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}