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
import com.sobey.cmdbuild.entity.CompanyHistory;
import com.sobey.cmdbuild.repository.CompanyHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.CompanyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * CompanyHistory的service类.
 */
@Service
@Transactional
public class CompanyHistoryService extends BasicSevcie {
	@Autowired
	private CompanyHistoryDao companyHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return CompanyHistory
	 */
	public CompanyHistory findCompanyHistory(Integer id) {
		return companyHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param companyHistory
	 * @return CompanyHistory
	 */
	public CompanyHistory saveOrUpdate(CompanyHistory companyHistory) {
		return companyHistoryDao.save(companyHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteCompanyHistory(Integer id) {
		companyHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return CompanyHistory
	 */
	public CompanyHistory findByCode(String code) {
		return companyHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<CompanyHistory>
	 */
	public List<CompanyHistory> getCompanies() {
		return companyHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<CompanyHistory>
	 */
	private Page<CompanyHistory> getCompanyHistoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<CompanyHistory> spec = buildSpecification(searchParams);
		return companyHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<CompanyHistory>
	 */
	private Specification<CompanyHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<CompanyHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				CompanyHistory.class);
		return spec;
	}

	/**
	 * CompanyHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<CompanyHistoryDTO>
	 */
	public PaginationResult<CompanyHistoryDTO> getCompanyHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<CompanyHistory> page = getCompanyHistoryPage(searchParams, pageNumber, pageSize); // 将List<CompanyHistory>中的数据转换为List<CompanyHistoryDTO>
		List<CompanyHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), CompanyHistoryDTO.class);
		PaginationResult<CompanyHistoryDTO> paginationResult = new PaginationResult<CompanyHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}