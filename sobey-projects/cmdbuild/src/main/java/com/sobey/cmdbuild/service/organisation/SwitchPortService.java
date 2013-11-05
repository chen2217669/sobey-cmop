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
import com.sobey.cmdbuild.entity.SwitchPort;
import com.sobey.cmdbuild.repository.SwitchPortDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.SwitchPortDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * SwitchPort的service类.
 */
@Service
@Transactional
public class SwitchPortService extends BasicSevcie {
	@Autowired
	private SwitchPortDao switchPortDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return SwitchPort
	 */
	public SwitchPort findSwitchPort(Integer id) {
		return switchPortDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param switchPort
	 * @return SwitchPort
	 */
	public SwitchPort saveOrUpdate(SwitchPort switchPort) {
		return switchPortDao.save(switchPort);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteSwitchPort(Integer id) {
		switchPortDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return SwitchPort
	 */
	public SwitchPort findByCode(String code) {
		return switchPortDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<SwitchPort>
	 */
	public List<SwitchPort> getCompanies() {
		return switchPortDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<SwitchPort>
	 */
	private Page<SwitchPort> getSwitchPortPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<SwitchPort> spec = buildSpecification(searchParams);
		return switchPortDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<SwitchPort>
	 */
	private Specification<SwitchPort> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<SwitchPort> spec = DynamicSpecifications.bySearchFilter(filters.values(), SwitchPort.class);
		return spec;
	}

	/**
	 * SwitchPortDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<SwitchPortDTO>
	 */
	public PaginationResult<SwitchPortDTO> getSwitchPortDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<SwitchPort> page = getSwitchPortPage(searchParams, pageNumber, pageSize); // 将List<SwitchPort>中的数据转换为List<SwitchPortDTO>
		List<SwitchPortDTO> dtos = BeanMapper.mapList(page.getContent(), SwitchPortDTO.class);
		PaginationResult<SwitchPortDTO> paginationResult = new PaginationResult<SwitchPortDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}