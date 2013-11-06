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
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.repository.RackDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.RackDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Rack的service类.
 */
@Service
@Transactional
public class RackService extends BasicSevcie {

	@Autowired
	private RackDao rackDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Rack
	 */
	public Rack findRack(Integer id) {
		return rackDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param rack
	 * @return Rack
	 */
	public Rack saveOrUpdate(Rack rack) {
		return rackDao.save(rack);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteRack(Integer id) {
		rackDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return Rack
	 */
	public Rack findByCode(String code) {
		return rackDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<Rack>
	 */
	public List<Rack> getRacks() {
		return rackDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Rack>
	 */
	private Page<Rack> getRackPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<Rack> spec = buildSpecification(searchParams);

		return rackDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Rack>
	 */
	private Specification<Rack> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), Rack.class);
	}

	/**
	 * RackDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<RackDTO>
	 */
	public PaginationResult<RackDTO> getRackDTOPagination(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		Page<Rack> page = getRackPage(searchParams, pageNumber, pageSize);

		List<RackDTO> dtos = BeanMapper.mapList(page.getContent(), RackDTO.class);

		return fillPaginationResult(page, dtos);
	}
}