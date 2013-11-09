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
import com.sobey.cmdbuild.entity.Esg;
import com.sobey.cmdbuild.repository.EsgDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.EsgDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Esg的service类.
 */
@Service
@Transactional
public class EsgService extends BasicSevcie {
	@Autowired
	private EsgDao esgDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Esg
	 */
	public Esg findEsg(Integer id) {
		return esgDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param esg
	 * @return Esg
	 */
	public Esg saveOrUpdate(Esg esg) {
		return esgDao.save(esg);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEsg(Integer id) {
		esgDao.delete(id);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Esg>
	 */
	private Page<Esg> getEsgPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Esg> spec = buildSpecification(searchParams);
		return esgDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Esg>
	 */
	private Specification<Esg> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Esg> spec = DynamicSpecifications.bySearchFilter(filters.values(), Esg.class);
		return spec;
	}

	/**
	 * EsgDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<EsgDTO>
	 */
	public PaginationResult<EsgDTO> getEsgDTOPagination(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<Esg> page = getEsgPage(searchParams, pageNumber, pageSize); // 将List<Esg>中的数据转换为List<EsgDTO>
		List<EsgDTO> dtos = BeanMapper.mapList(page.getContent(), EsgDTO.class);
		PaginationResult<EsgDTO> paginationResult = new PaginationResult<EsgDTO>(page.getNumber(), page.getSize(),
				page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(), page.hasPreviousPage(),
				page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}