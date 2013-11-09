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
import com.sobey.cmdbuild.entity.Cs2;
import com.sobey.cmdbuild.repository.Cs2Dao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.Cs2DTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Cs2的service类.
 */
@Service
@Transactional
public class Cs2Service extends BasicSevcie {
	@Autowired
	private Cs2Dao cs2Dao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Cs2
	 */
	public Cs2 findCs2(Integer id) {
		return cs2Dao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param cs2
	 * @return Cs2
	 */
	public Cs2 saveOrUpdate(Cs2 cs2) {
		return cs2Dao.save(cs2);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteCs2(Integer id) {
		cs2Dao.delete(id);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Cs2>
	 */
	private Page<Cs2> getCs2Page(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Cs2> spec = buildSpecification(searchParams);
		return cs2Dao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Cs2>
	 */
	private Specification<Cs2> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Cs2> spec = DynamicSpecifications.bySearchFilter(filters.values(), Cs2.class);
		return spec;
	}

	/**
	 * Cs2DTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<Cs2DTO>
	 */
	public PaginationResult<Cs2DTO> getCs2DTOPagination(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<Cs2> page = getCs2Page(searchParams, pageNumber, pageSize); // 将List<Cs2>中的数据转换为List<Cs2DTO>
		List<Cs2DTO> dtos = BeanMapper.mapList(page.getContent(), Cs2DTO.class);
		PaginationResult<Cs2DTO> paginationResult = new PaginationResult<Cs2DTO>(page.getNumber(), page.getSize(),
				page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(), page.hasPreviousPage(),
				page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}