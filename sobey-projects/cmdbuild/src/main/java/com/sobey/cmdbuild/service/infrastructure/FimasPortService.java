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
import com.sobey.cmdbuild.entity.FimasPort;
import com.sobey.cmdbuild.repository.FimasPortDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.FimasPortDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * FimasPort的service类.
 */
@Service
@Transactional
public class FimasPortService extends BasicSevcie {
	@Autowired
	private FimasPortDao fimasPortDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return FimasPort
	 */
	public FimasPort findFimasPort(Integer id) {
		return fimasPortDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param fimasPort
	 * @return FimasPort
	 */
	public FimasPort saveOrUpdate(FimasPort fimasPort) {
		return fimasPortDao.save(fimasPort);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteFimasPort(Integer id) {
		fimasPortDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return FimasPort
	 */
	public FimasPort findByCode(String code) {
		return fimasPortDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<FimasPort>
	 */
	public List<FimasPort> getCompanies() {
		return fimasPortDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<FimasPort>
	 */
	private Page<FimasPort> getFimasPortPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<FimasPort> spec = buildSpecification(searchParams);
		return fimasPortDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<FimasPort>
	 */
	private Specification<FimasPort> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<FimasPort> spec = DynamicSpecifications.bySearchFilter(filters.values(), FimasPort.class);
		return spec;
	}

	/**
	 * FimasPortDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<FimasPortDTO>
	 */
	public PaginationResult<FimasPortDTO> getFimasPortDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<FimasPort> page = getFimasPortPage(searchParams, pageNumber, pageSize); // 将List<FimasPort>中的数据转换为List<FimasPortDTO>
		List<FimasPortDTO> dtos = BeanMapper.mapList(page.getContent(), FimasPortDTO.class);
		PaginationResult<FimasPortDTO> paginationResult = new PaginationResult<FimasPortDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}