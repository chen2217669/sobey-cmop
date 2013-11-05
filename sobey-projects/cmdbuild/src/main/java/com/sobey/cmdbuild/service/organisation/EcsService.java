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
import com.sobey.cmdbuild.entity.Ecs;
import com.sobey.cmdbuild.repository.EcsDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.EcsDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Ecs的service类.
 */
@Service
@Transactional
public class EcsService extends BasicSevcie {
	@Autowired
	private EcsDao ecsDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Ecs
	 */
	public Ecs findEcs(Integer id) {
		return ecsDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param ecs
	 * @return Ecs
	 */
	public Ecs saveOrUpdate(Ecs ecs) {
		return ecsDao.save(ecs);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEcs(Integer id) {
		ecsDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return Ecs
	 */
	public Ecs findByCode(String code) {
		return ecsDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<Ecs>
	 */
	public List<Ecs> getCompanies() {
		return ecsDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Ecs>
	 */
	private Page<Ecs> getEcsPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Ecs> spec = buildSpecification(searchParams);
		return ecsDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Ecs>
	 */
	private Specification<Ecs> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Ecs> spec = DynamicSpecifications.bySearchFilter(filters.values(), Ecs.class);
		return spec;
	}

	/**
	 * EcsDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<EcsDTO>
	 */
	public PaginationResult<EcsDTO> getEcsDTOPagination(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<Ecs> page = getEcsPage(searchParams, pageNumber, pageSize); // 将List<Ecs>中的数据转换为List<EcsDTO>
		List<EcsDTO> dtos = BeanMapper.mapList(page.getContent(), EcsDTO.class);
		PaginationResult<EcsDTO> paginationResult = new PaginationResult<EcsDTO>(page.getNumber(), page.getSize(),
				page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(), page.hasPreviousPage(),
				page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}