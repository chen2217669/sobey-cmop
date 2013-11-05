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
import com.sobey.cmdbuild.entity.Es3Spec;
import com.sobey.cmdbuild.repository.Es3SpecDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.Es3SpecDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Es3Spec的service类.
 */
@Service
@Transactional
public class Es3SpecService extends BasicSevcie {
	@Autowired
	private Es3SpecDao es3SpecDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Es3Spec
	 */
	public Es3Spec findEs3Spec(Integer id) {
		return es3SpecDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param es3Spec
	 * @return Es3Spec
	 */
	public Es3Spec saveOrUpdate(Es3Spec es3Spec) {
		return es3SpecDao.save(es3Spec);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEs3Spec(Integer id) {
		es3SpecDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return Es3Spec
	 */
	public Es3Spec findByCode(String code) {
		return es3SpecDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<Es3Spec>
	 */
	public List<Es3Spec> getCompanies() {
		return es3SpecDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Es3Spec>
	 */
	private Page<Es3Spec> getEs3SpecPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Es3Spec> spec = buildSpecification(searchParams);
		return es3SpecDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Es3Spec>
	 */
	private Specification<Es3Spec> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Es3Spec> spec = DynamicSpecifications.bySearchFilter(filters.values(), Es3Spec.class);
		return spec;
	}

	/**
	 * Es3SpecDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<Es3SpecDTO>
	 */
	public PaginationResult<Es3SpecDTO> getEs3SpecDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<Es3Spec> page = getEs3SpecPage(searchParams, pageNumber, pageSize); // 将List<Es3Spec>中的数据转换为List<Es3SpecDTO>
		List<Es3SpecDTO> dtos = BeanMapper.mapList(page.getContent(), Es3SpecDTO.class);
		PaginationResult<Es3SpecDTO> paginationResult = new PaginationResult<Es3SpecDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}