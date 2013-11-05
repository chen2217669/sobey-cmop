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
import com.sobey.cmdbuild.entity.Idc;
import com.sobey.cmdbuild.repository.IdcDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Idc的service类.
 */
@Service
@Transactional
public class IdcService extends BasicSevcie {
	@Autowired
	private IdcDao idcDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Idc
	 */
	public Idc findIdc(Integer id) {
		return idcDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param idc
	 * @return Idc
	 */
	public Idc saveOrUpdate(Idc idc) {
		return idcDao.save(idc);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteIdc(Integer id) {
		idcDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return Idc
	 */
	public Idc findByCode(String code) {
		return idcDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<Idc>
	 */
	public List<Idc> getCompanies() {
		return idcDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Idc>
	 */
	private Page<Idc> getIdcPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Idc> spec = buildSpecification(searchParams);
		return idcDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Idc>
	 */
	private Specification<Idc> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Idc> spec = DynamicSpecifications.bySearchFilter(filters.values(), Idc.class);
		return spec;
	}

	/**
	 * IdcDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<IdcDTO>
	 */
	public PaginationResult<IdcDTO> getIdcDTOPagination(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<Idc> page = getIdcPage(searchParams, pageNumber, pageSize); // 将List<Idc>中的数据转换为List<IdcDTO>
		List<IdcDTO> dtos = BeanMapper.mapList(page.getContent(), IdcDTO.class);
		PaginationResult<IdcDTO> paginationResult = new PaginationResult<IdcDTO>(page.getNumber(), page.getSize(),
				page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(), page.hasPreviousPage(),
				page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}