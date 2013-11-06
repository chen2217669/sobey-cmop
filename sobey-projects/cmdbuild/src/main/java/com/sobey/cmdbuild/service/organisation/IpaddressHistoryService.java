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
import com.sobey.cmdbuild.entity.IpaddressHistory;
import com.sobey.cmdbuild.repository.IpaddressHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.IpaddressHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * IpaddressHistory的service类.
 */
@Service
@Transactional
public class IpaddressHistoryService extends BasicSevcie {
	@Autowired
	private IpaddressHistoryDao ipaddressHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return IpaddressHistory
	 */
	public IpaddressHistory findIpaddressHistory(Integer id) {
		return ipaddressHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param ipaddressHistory
	 * @return IpaddressHistory
	 */
	public IpaddressHistory saveOrUpdate(IpaddressHistory ipaddressHistory) {
		return ipaddressHistoryDao.save(ipaddressHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteIpaddressHistory(Integer id) {
		ipaddressHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return IpaddressHistory
	 */
	public IpaddressHistory findByCode(String code) {
		return ipaddressHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<IpaddressHistory>
	 */
	public List<IpaddressHistory> getCompanies() {
		return ipaddressHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<IpaddressHistory>
	 */
	private Page<IpaddressHistory> getIpaddressHistoryPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<IpaddressHistory> spec = buildSpecification(searchParams);
		return ipaddressHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<IpaddressHistory>
	 */
	private Specification<IpaddressHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<IpaddressHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				IpaddressHistory.class);
		return spec;
	}

	/**
	 * IpaddressHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<IpaddressHistoryDTO>
	 */
	public PaginationResult<IpaddressHistoryDTO> getIpaddressHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<IpaddressHistory> page = getIpaddressHistoryPage(searchParams, pageNumber, pageSize); // 将List<IpaddressHistory>中的数据转换为List<IpaddressHistoryDTO>
		List<IpaddressHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), IpaddressHistoryDTO.class);
		PaginationResult<IpaddressHistoryDTO> paginationResult = new PaginationResult<IpaddressHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}