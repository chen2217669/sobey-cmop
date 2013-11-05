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
import com.sobey.cmdbuild.entity.VlanHistory;
import com.sobey.cmdbuild.repository.VlanHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.VlanHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * VlanHistory的service类.
 */
@Service
@Transactional
public class VlanHistoryService extends BasicSevcie {
	@Autowired
	private VlanHistoryDao vlanHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return VlanHistory
	 */
	public VlanHistory findVlanHistory(Integer id) {
		return vlanHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param vlanHistory
	 * @return VlanHistory
	 */
	public VlanHistory saveOrUpdate(VlanHistory vlanHistory) {
		return vlanHistoryDao.save(vlanHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteVlanHistory(Integer id) {
		vlanHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return VlanHistory
	 */
	public VlanHistory findByCode(String code) {
		return vlanHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<VlanHistory>
	 */
	public List<VlanHistory> getCompanies() {
		return vlanHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<VlanHistory>
	 */
	private Page<VlanHistory> getVlanHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<VlanHistory> spec = buildSpecification(searchParams);
		return vlanHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<VlanHistory>
	 */
	private Specification<VlanHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<VlanHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(), VlanHistory.class);
		return spec;
	}

	/**
	 * VlanHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<VlanHistoryDTO>
	 */
	public PaginationResult<VlanHistoryDTO> getVlanHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<VlanHistory> page = getVlanHistoryPage(searchParams, pageNumber, pageSize); // 将List<VlanHistory>中的数据转换为List<VlanHistoryDTO>
		List<VlanHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), VlanHistoryDTO.class);
		PaginationResult<VlanHistoryDTO> paginationResult = new PaginationResult<VlanHistoryDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}