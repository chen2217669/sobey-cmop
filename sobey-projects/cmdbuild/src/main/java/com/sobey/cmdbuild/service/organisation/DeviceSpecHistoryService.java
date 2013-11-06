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
import com.sobey.cmdbuild.entity.DeviceSpecHistory;
import com.sobey.cmdbuild.repository.DeviceSpecHistoryDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * DeviceSpecHistory的service类.
 */
@Service
@Transactional
public class DeviceSpecHistoryService extends BasicSevcie {
	@Autowired
	private DeviceSpecHistoryDao deviceSpecHistoryDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return DeviceSpecHistory
	 */
	public DeviceSpecHistory findDeviceSpecHistory(Integer id) {
		return deviceSpecHistoryDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param deviceSpecHistory
	 * @return DeviceSpecHistory
	 */
	public DeviceSpecHistory saveOrUpdate(DeviceSpecHistory deviceSpecHistory) {
		return deviceSpecHistoryDao.save(deviceSpecHistory);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteDeviceSpecHistory(Integer id) {
		deviceSpecHistoryDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return DeviceSpecHistory
	 */
	public DeviceSpecHistory findByCode(String code) {
		return deviceSpecHistoryDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<DeviceSpecHistory>
	 */
	public List<DeviceSpecHistory> getCompanies() {
		return deviceSpecHistoryDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<DeviceSpecHistory>
	 */
	private Page<DeviceSpecHistory> getDeviceSpecHistoryPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<DeviceSpecHistory> spec = buildSpecification(searchParams);
		return deviceSpecHistoryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<DeviceSpecHistory>
	 */
	private Specification<DeviceSpecHistory> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<DeviceSpecHistory> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				DeviceSpecHistory.class);
		return spec;
	}

	/**
	 * DeviceSpecHistoryDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<DeviceSpecHistoryDTO>
	 */
	public PaginationResult<DeviceSpecHistoryDTO> getDeviceSpecHistoryDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<DeviceSpecHistory> page = getDeviceSpecHistoryPage(searchParams, pageNumber, pageSize); // 将List<DeviceSpecHistory>中的数据转换为List<DeviceSpecHistoryDTO>
		List<DeviceSpecHistoryDTO> dtos = BeanMapper.mapList(page.getContent(), DeviceSpecHistoryDTO.class);
		PaginationResult<DeviceSpecHistoryDTO> paginationResult = new PaginationResult<DeviceSpecHistoryDTO>(
				page.getNumber(), page.getSize(), page.getTotalPages(), page.getNumberOfElements(),
				page.getNumberOfElements(), page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(),
				page.isLastPage(), dtos);
		return paginationResult;
	}
}