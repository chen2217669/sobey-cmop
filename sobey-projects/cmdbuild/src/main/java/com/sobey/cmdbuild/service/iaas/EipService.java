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
import com.sobey.cmdbuild.entity.Eip;
import com.sobey.cmdbuild.repository.EipDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.EipDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Eip的service类.
 */
@Service
@Transactional
public class EipService extends BasicSevcie {
	@Autowired
	private EipDao eipDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Eip
	 */
	public Eip findEip(Integer id) {
		return eipDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param eip
	 * @return Eip
	 */
	public Eip saveOrUpdate(Eip eip) {
		return eipDao.save(eip);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteEip(Integer id) {
		eipDao.delete(id);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Eip>
	 */
	private Page<Eip> getEipPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Eip> spec = buildSpecification(searchParams);
		return eipDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Eip>
	 */
	private Specification<Eip> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Eip> spec = DynamicSpecifications.bySearchFilter(filters.values(), Eip.class);
		return spec;
	}

	/**
	 * EipDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<EipDTO>
	 */
	public PaginationResult<EipDTO> getEipDTOPagination(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<Eip> page = getEipPage(searchParams, pageNumber, pageSize); // 将List<Eip>中的数据转换为List<EipDTO>
		List<EipDTO> dtos = BeanMapper.mapList(page.getContent(), EipDTO.class);
		PaginationResult<EipDTO> paginationResult = new PaginationResult<EipDTO>(page.getNumber(), page.getSize(),
				page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(), page.hasPreviousPage(),
				page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}