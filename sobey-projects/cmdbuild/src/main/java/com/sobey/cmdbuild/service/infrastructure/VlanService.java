package com.sobey.cmdbuild.service.infrastructure;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.Vlan;
import com.sobey.cmdbuild.repository.VlanDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.VlanDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Vlan的service类.
 */
@Service
@Transactional
public class VlanService extends BasicSevcie {
	@Autowired
	private VlanDao vlanDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Vlan
	 */
	public Vlan findVlan(Integer id) {
		return vlanDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param vlan
	 * @return Vlan
	 */
	public Vlan saveOrUpdate(Vlan vlan) {
		return vlanDao.save(vlan);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteVlan(Integer id) {
		vlanDao.delete(id);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Vlan>
	 */
	private Page<Vlan> getVlanPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Vlan> spec = buildSpecification(searchParams);
		return vlanDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Vlan>
	 */
	private Specification<Vlan> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Vlan> spec = DynamicSpecifications.bySearchFilter(filters.values(), Vlan.class);
		return spec;
	}

	/**
	 * VlanDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<VlanDTO>
	 */
	public PaginationResult<VlanDTO> getVlanDTOPagination(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<Vlan> page = getVlanPage(searchParams, pageNumber, pageSize); // 将List<Vlan>中的数据转换为List<VlanDTO>
		List<VlanDTO> dtos = BeanMapper.mapList(page.getContent(), VlanDTO.class);
		PaginationResult<VlanDTO> paginationResult = new PaginationResult<VlanDTO>(page.getNumber(), page.getSize(),
				page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(), page.hasPreviousPage(),
				page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}