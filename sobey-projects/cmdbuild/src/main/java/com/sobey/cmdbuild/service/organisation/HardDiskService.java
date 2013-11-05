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
	 * 新增、保存对象
	 * 
	 * @param hardDisk
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
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return HardDisk
	 */
	public HardDisk findByCode(String code) {
		return hardDiskDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<HardDisk>
	 */
	public List<HardDisk> getCompanies() {
		return hardDiskDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
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
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<HardDisk>
	 */
	private Specification<HardDisk> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<HardDisk> spec = DynamicSpecifications.bySearchFilter(filters.values(), HardDisk.class);
		return spec;
	}

	/**
	 * HardDiskDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<HardDiskDTO>
	 */
	public PaginationResult<HardDiskDTO> getHardDiskDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<HardDisk> page = getHardDiskPage(searchParams, pageNumber, pageSize); // 将List<HardDisk>中的数据转换为List<HardDiskDTO>
		List<HardDiskDTO> dtos = BeanMapper.mapList(page.getContent(), HardDiskDTO.class);
		PaginationResult<HardDiskDTO> paginationResult = new PaginationResult<HardDiskDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}