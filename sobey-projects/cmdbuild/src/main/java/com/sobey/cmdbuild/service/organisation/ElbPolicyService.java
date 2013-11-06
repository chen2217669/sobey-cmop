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
import com.sobey.cmdbuild.entity.ElbPolicy;
import com.sobey.cmdbuild.repository.ElbPolicyDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.ElbPolicyDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * ElbPolicy的service类.
 */
@Service
@Transactional
public class ElbPolicyService extends BasicSevcie {
	@Autowired
	private ElbPolicyDao elbPolicyDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return ElbPolicy
	 */
	public ElbPolicy findElbPolicy(Integer id) {
		return elbPolicyDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param elbPolicy
	 * @return ElbPolicy
	 */
	public ElbPolicy saveOrUpdate(ElbPolicy elbPolicy) {
		return elbPolicyDao.save(elbPolicy);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteElbPolicy(Integer id) {
		elbPolicyDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return ElbPolicy
	 */
	public ElbPolicy findByCode(String code) {
		return elbPolicyDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<ElbPolicy>
	 */
	public List<ElbPolicy> getCompanies() {
		return elbPolicyDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<ElbPolicy>
	 */
	private Page<ElbPolicy> getElbPolicyPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<ElbPolicy> spec = buildSpecification(searchParams);
		return elbPolicyDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<ElbPolicy>
	 */
	private Specification<ElbPolicy> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<ElbPolicy> spec = DynamicSpecifications.bySearchFilter(filters.values(), ElbPolicy.class);
		return spec;
	}

	/**
	 * ElbPolicyDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<ElbPolicyDTO>
	 */
	public PaginationResult<ElbPolicyDTO> getElbPolicyDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<ElbPolicy> page = getElbPolicyPage(searchParams, pageNumber, pageSize); // 将List<ElbPolicy>中的数据转换为List<ElbPolicyDTO>
		List<ElbPolicyDTO> dtos = BeanMapper.mapList(page.getContent(), ElbPolicyDTO.class);
		PaginationResult<ElbPolicyDTO> paginationResult = new PaginationResult<ElbPolicyDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}