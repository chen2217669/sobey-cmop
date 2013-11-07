package com.sobey.cmdbuild.service.financial;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.EcsSpec;
import com.sobey.cmdbuild.repository.EcsSpecDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * EcsSpec的service类.
 */
@Service
@Transactional
public class EcsSpecService extends BasicSevcie {
	@Autowired
	private EcsSpecDao ecsSpecDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return EcsSpec
	 */
	public EcsSpec findEcsSpec(Integer id) {
		return ecsSpecDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param ecsSpec
	 * @return EcsSpec
	 */
	public EcsSpec saveOrUpdate(EcsSpec ecsSpec) {
		return ecsSpecDao.save(ecsSpec);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEcsSpec(Integer id) {
		ecsSpecDao.delete(id);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<EcsSpec>
	 */
	private Page<EcsSpec> getEcsSpecPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<EcsSpec> spec = buildSpecification(searchParams);
		return ecsSpecDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<EcsSpec>
	 */
	private Specification<EcsSpec> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<EcsSpec> spec = DynamicSpecifications.bySearchFilter(filters.values(), EcsSpec.class);
		return spec;
	}

	/**
	 * EcsSpecDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<EcsSpecDTO>
	 */
	public PaginationResult<EcsSpecDTO> getEcsSpecDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<EcsSpec> page = getEcsSpecPage(searchParams, pageNumber, pageSize); // 将List<EcsSpec>中的数据转换为List<EcsSpecDTO>
		List<EcsSpecDTO> dtos = BeanMapper.mapList(page.getContent(), EcsSpecDTO.class);
		PaginationResult<EcsSpecDTO> paginationResult = new PaginationResult<EcsSpecDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}