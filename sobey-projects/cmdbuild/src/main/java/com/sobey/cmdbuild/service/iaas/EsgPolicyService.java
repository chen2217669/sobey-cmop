package com.sobey.cmdbuild.service.iaas;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.EsgPolicy;
import com.sobey.cmdbuild.repository.EsgPolicyDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.EsgPolicyDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * EsgPolicy的service类.
 */
@Service
@Transactional
public class EsgPolicyService extends BasicSevcie {
	@Autowired
	private EsgPolicyDao esgPolicyDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return EsgPolicy
	 */
	public EsgPolicy findEsgPolicy(Integer id) {
		return esgPolicyDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param esgPolicy
	 * @return EsgPolicy
	 */
	public EsgPolicy saveOrUpdate(EsgPolicy esgPolicy) {
		return esgPolicyDao.save(esgPolicy);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEsgPolicy(Integer id) {
		esgPolicyDao.delete(id);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<EsgPolicy>
	 */
	private Page<EsgPolicy> getEsgPolicyPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<EsgPolicy> spec = buildSpecification(searchParams);
		return esgPolicyDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<EsgPolicy>
	 */
	private Specification<EsgPolicy> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<EsgPolicy> spec = DynamicSpecifications.bySearchFilter(filters.values(), EsgPolicy.class);
		return spec;
	}

	/**
	 * EsgPolicyDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<EsgPolicyDTO>
	 */
	public PaginationResult<EsgPolicyDTO> getEsgPolicyDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<EsgPolicy> page = getEsgPolicyPage(searchParams, pageNumber, pageSize); // 将List<EsgPolicy>中的数据转换为List<EsgPolicyDTO>
		List<EsgPolicyDTO> dtos = BeanMapper.mapList(page.getContent(), EsgPolicyDTO.class);
		PaginationResult<EsgPolicyDTO> paginationResult = new PaginationResult<EsgPolicyDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}