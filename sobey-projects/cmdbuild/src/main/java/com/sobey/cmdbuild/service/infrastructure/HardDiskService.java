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
import com.sobey.cmdbuild.entity.HardDisk;
import com.sobey.cmdbuild.repository.HardDiskDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.HardDiskDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * HardDisk的service类.
 */
@Service
@Transactional
public class HardDiskService extends BasicSevcie {
	@Autowired
	private HardDiskDao hardDiskDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return HardDisk
	 */
	public HardDisk findHardDisk(Integer id) {
		return hardDiskDao.findOne(id);
	}

	/**
	 * 根据自定义动态查询条件获得对象.
	 * 
	 * 将条件查询放入searchParams中. 查询条件可查询{@link SearchFilter}类.
	 * 
	 * <pre>
	 * searchParams.put(&quot;EQ_status&quot;, 'A');
	 * </pre>
	 * 
	 * @param searchParams
	 *            动态查询条件Map
	 * @return HardDisk
	 */
	public HardDisk findHardDisk(Map<String, Object> searchParams) {
		return hardDiskDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param HardDisk
	 * @return HardDisk
	 */
	public HardDisk saveOrUpdate(HardDisk hardDisk) {
		return hardDiskDao.save(hardDisk);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteHardDisk(Integer id) {
		hardDiskDao.delete(id);
	}

	/**
	 * 根据自定义动态查询条件获得对象集合.
	 * 
	 * 将条件查询放入searchParams中. 查询条件可查询{@link SearchFilter}类.
	 * 
	 * <pre>
	 * searchParams.put(&quot;EQ_status&quot;, 'A');
	 * </pre>
	 * 
	 * @param searchParams
	 *            动态查询条件Map * @return List<HardDisk>
	 */
	public List<HardDisk> getHardDiskList(Map<String, Object> searchParams) {
		return hardDiskDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<HardDisk>
	 */
	private Page<HardDisk> getHardDiskPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<HardDisk> spec = buildSpecification(searchParams);

		return hardDiskDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<HardDisk>
	 */
	private Specification<HardDisk> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), HardDisk.class);
	}

	/**
	 * HardDiskDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<HardDiskDTO>
	 */
	public PaginationResult<HardDiskDTO> getHardDiskDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<HardDisk> page = getHardDiskPage(searchParams, pageNumber, pageSize);

		List<HardDiskDTO> dtos = BeanMapper.mapList(page.getContent(), HardDiskDTO.class);

		return fillPaginationResult(page, dtos);
	}
}