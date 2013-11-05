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
import com.sobey.cmdbuild.entity.ConsumptionsHistory;
import com.sobey.cmdbuild.repository.ConsumptionsHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.ConsumptionsHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * ConsumptionsHistory的service类.
 */
@Service
@Transactional
public class ConsumptionsHistoryService extends BasicSevcie {
	@Autowired
	private ConsumptionsHistoryDao consumptionsHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return ConsumptionsHistory
	 */
	public ConsumptionsHistory findConsumptionsHistory(Integer id) {
		return consumptionsHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param consumptionsHistory
	 * @return ConsumptionsHistory
	 */
	public ConsumptionsHistory saveOrUpdate(ConsumptionsHistory consumptionsHistory) {
		return consumptionsHistoryDao.save(consumptionsHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteConsumptionsHistory(Integer id) {
		consumptionsHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return ConsumptionsHistory
	 */
	public ConsumptionsHistory findByCode(String code) {
		return consumptionsHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<ConsumptionsHistory>
	 */
	public List<ConsumptionsHistory> getCompanies() {
		return consumptionsHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<ConsumptionsHistory>
	 */
	private Page<ConsumptionsHistory> getConsumptionsHistoryPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<ConsumptionsHistory> spec = buildSpecification(searchParams);
		return consumptionsHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<ConsumptionsHistory>
	 */
	private Specification<ConsumptionsHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<ConsumptionsHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				ConsumptionsHistory.class);
		return spec;
	}

	/**
	 * ConsumptionsHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<ConsumptionsHistoryDTO>
	 */
	public PaginationResult<ConsumptionsHistoryDTO> getConsumptionsHistoryDTOPagination(
			Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<ConsumptionsHistory> page = getConsumptionsHistoryPage(searchParams, pageNumber, pageSize); // 将List<ConsumptionsHistory>中的数据转换为List<ConsumptionsHistoryDTO>
		List<ConsumptionsHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), ConsumptionsHistoryDTO.class);
		PaginationResult<ConsumptionsHistoryDTO> paginationResult = new PaginationResult<ConsumptionsHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}