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
import com.sobey.cmdbuild.entity.Memory;
import com.sobey.cmdbuild.repository.MemoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.MemoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Memory的service类.
 */
@Service
@Transactional
public class MemoryService extends BasicSevcie {
	@Autowired
	private MemoryDao memoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Memory
	 */
	public Memory findMemory(Integer id) {
		return memoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param memory
	 * @return Memory
	 */
	public Memory saveOrUpdate(Memory memory) {
		return memoryDao.save(memory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteMemory(Integer id) {
		memoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return Memory
	 */
	public Memory findByCode(String code) {
		return memoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<Memory>
	 */
	public List<Memory> getCompanies() {
		return memoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Memory>
	 */
	private Page<Memory> getMemoryPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Memory> spec = buildSpecification(searchParams);
		return memoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Memory>
	 */
	private Specification<Memory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Memory> spec = DynamicSpecifications.bySearchFilter(filters.values(), Memory.class);
		return spec;
	}

	/**
	 * MemoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<MemoryDTO>
	 */
	public PaginationResult<MemoryDTO> getMemoryDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<Memory> page = getMemoryPage(searchParams, pageNumber, pageSize); // 将List<Memory>中的数据转换为List<MemoryDTO>
		List<MemoryDTO> dtos = BeanMapper.mapList(page.getContent(), MemoryDTO.class);
		PaginationResult<MemoryDTO> paginationResult = new PaginationResult<MemoryDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}