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
import com.sobey.cmdbuild.entity.EipSpec;
import com.sobey.cmdbuild.repository.EipSpecDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * EipSpec的service类.
 */
@Service
@Transactional
public class EipSpecService extends BasicSevcie {
	@Autowired
	private EipSpecDao eipSpecDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return EipSpec
	 */
	public EipSpec findEipSpec(Integer id) {
		return eipSpecDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param eipSpec
	 * @return EipSpec
	 */
	public EipSpec saveOrUpdate(EipSpec eipSpec) {
		return eipSpecDao.save(eipSpec);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEipSpec(Integer id) {
		eipSpecDao.delete(id);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<EipSpec>
	 */
	private Page<EipSpec> getEipSpecPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<EipSpec> spec = buildSpecification(searchParams);
		return eipSpecDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<EipSpec>
	 */
	private Specification<EipSpec> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<EipSpec> spec = DynamicSpecifications.bySearchFilter(filters.values(), EipSpec.class);
		return spec;
	}

	/**
	 * EipSpecDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<EipSpecDTO>
	 */
	public PaginationResult<EipSpecDTO> getEipSpecDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<EipSpec> page = getEipSpecPage(searchParams, pageNumber, pageSize); // 将List<EipSpec>中的数据转换为List<EipSpecDTO>
		List<EipSpecDTO> dtos = BeanMapper.mapList(page.getContent(), EipSpecDTO.class);
		PaginationResult<EipSpecDTO> paginationResult = new PaginationResult<EipSpecDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}