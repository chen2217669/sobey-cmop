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
import com.sobey.cmdbuild.entity.NicPort;
import com.sobey.cmdbuild.repository.NicPortDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.NicPortDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * NicPort的service类.
 */
@Service
@Transactional
public class NicPortService extends BasicSevcie {
	@Autowired
	private NicPortDao nicPortDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return NicPort
	 */
	public NicPort findNicPort(Integer id) {
		return nicPortDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param nicPort
	 * @return NicPort
	 */
	public NicPort saveOrUpdate(NicPort nicPort) {
		return nicPortDao.save(nicPort);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteNicPort(Integer id) {
		nicPortDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return NicPort
	 */
	public NicPort findByCode(String code) {
		return nicPortDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<NicPort>
	 */
	public List<NicPort> getCompanies() {
		return nicPortDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<NicPort>
	 */
	private Page<NicPort> getNicPortPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<NicPort> spec = buildSpecification(searchParams);
		return nicPortDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<NicPort>
	 */
	private Specification<NicPort> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<NicPort> spec = DynamicSpecifications.bySearchFilter(filters.values(), NicPort.class);
		return spec;
	}

	/**
	 * NicPortDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<NicPortDTO>
	 */
	public PaginationResult<NicPortDTO> getNicPortDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<NicPort> page = getNicPortPage(searchParams, pageNumber, pageSize); // 将List<NicPort>中的数据转换为List<NicPortDTO>
		List<NicPortDTO> dtos = BeanMapper.mapList(page.getContent(), NicPortDTO.class);
		PaginationResult<NicPortDTO> paginationResult = new PaginationResult<NicPortDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}