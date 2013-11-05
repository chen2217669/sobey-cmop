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
import com.sobey.cmdbuild.entity.HardDiskHistory;
import com.sobey.cmdbuild.repository.HardDiskHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.HardDiskHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * HardDiskHistory的service类.
 */
@Service
@Transactional
public class HardDiskHistoryService extends BasicSevcie {
	@Autowired
	private HardDiskHistoryDao hardDiskHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return HardDiskHistory
	 */
	public HardDiskHistory findHardDiskHistory(Integer id) {
		return hardDiskHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param hardDiskHistory
	 * @return HardDiskHistory
	 */
	public HardDiskHistory saveOrUpdate(HardDiskHistory hardDiskHistory) {
		return hardDiskHistoryDao.save(hardDiskHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteHardDiskHistory(Integer id) {
		hardDiskHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return HardDiskHistory
	 */
	public HardDiskHistory findByCode(String code) {
		return hardDiskHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<HardDiskHistory>
	 */
	public List<HardDiskHistory> getCompanies() {
		return hardDiskHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<HardDiskHistory>
	 */
	private Page<HardDiskHistory> getHardDiskHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<HardDiskHistory> spec = buildSpecification(searchParams);
		return hardDiskHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<HardDiskHistory>
	 */
	private Specification<HardDiskHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<HardDiskHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				HardDiskHistory.class);
		return spec;
	}

	/**
	 * HardDiskHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<HardDiskHistoryDTO>
	 */
	public PaginationResult<HardDiskHistoryDTO> getHardDiskHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<HardDiskHistory> page = getHardDiskHistoryPage(searchParams, pageNumber, pageSize); // 将List<HardDiskHistory>中的数据转换为List<HardDiskHistoryDTO>
		List<HardDiskHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), HardDiskHistoryDTO.class);
		PaginationResult<HardDiskHistoryDTO> paginationResult = new PaginationResult<HardDiskHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}