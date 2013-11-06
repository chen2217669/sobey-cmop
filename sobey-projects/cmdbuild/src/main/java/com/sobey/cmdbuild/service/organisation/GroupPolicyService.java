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
import com.sobey.cmdbuild.entity.GroupPolicy;
import com.sobey.cmdbuild.repository.GroupPolicyDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.GroupPolicyDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * GroupPolicy的service类.
 */
@Service
@Transactional
public class GroupPolicyService extends BasicSevcie {
	@Autowired
	private GroupPolicyDao groupPolicyDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return GroupPolicy
	 */
	public GroupPolicy findGroupPolicy(Integer id) {
		return groupPolicyDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param groupPolicy
	 * @return GroupPolicy
	 */
	public GroupPolicy saveOrUpdate(GroupPolicy groupPolicy) {
		return groupPolicyDao.save(groupPolicy);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteGroupPolicy(Integer id) {
		groupPolicyDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return GroupPolicy
	 */
	public GroupPolicy findByCode(String code) {
		return groupPolicyDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<GroupPolicy>
	 */
	public List<GroupPolicy> getCompanies() {
		return groupPolicyDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<GroupPolicy>
	 */
	private Page<GroupPolicy> getGroupPolicyPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<GroupPolicy> spec = buildSpecification(searchParams);
		return groupPolicyDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<GroupPolicy>
	 */
	private Specification<GroupPolicy> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<GroupPolicy> spec = DynamicSpecifications.bySearchFilter(filters.values(), GroupPolicy.class);
		return spec;
	}

	/**
	 * GroupPolicyDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<GroupPolicyDTO>
	 */
	public PaginationResult<GroupPolicyDTO> getGroupPolicyDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<GroupPolicy> page = getGroupPolicyPage(searchParams, pageNumber, pageSize); // 将List<GroupPolicy>中的数据转换为List<GroupPolicyDTO>
		List<GroupPolicyDTO> dtos = BeanMapper.mapList(page.getContent(), GroupPolicyDTO.class);
		PaginationResult<GroupPolicyDTO> paginationResult = new PaginationResult<GroupPolicyDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}