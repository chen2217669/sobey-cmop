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
import com.sobey.cmdbuild.entity.NetappPort;
import com.sobey.cmdbuild.repository.NetappPortDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.NetappPortDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * NetappPort的service类.
 */
@Service
@Transactional
public class NetappPortService extends BasicSevcie {
	@Autowired
	private NetappPortDao netappPortDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return NetappPort
	 */
	public NetappPort findNetappPort(Integer id) {
		return netappPortDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param netappPort
	 * @return NetappPort
	 */
	public NetappPort saveOrUpdate(NetappPort netappPort) {
		return netappPortDao.save(netappPort);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteNetappPort(Integer id) {
		netappPortDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return NetappPort
	 */
	public NetappPort findByCode(String code) {
		return netappPortDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<NetappPort>
	 */
	public List<NetappPort> getCompanies() {
		return netappPortDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<NetappPort>
	 */
	private Page<NetappPort> getNetappPortPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<NetappPort> spec = buildSpecification(searchParams);
		return netappPortDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<NetappPort>
	 */
	private Specification<NetappPort> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<NetappPort> spec = DynamicSpecifications.bySearchFilter(filters.values(), NetappPort.class);
		return spec;
	}

	/**
	 * NetappPortDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<NetappPortDTO>
	 */
	public PaginationResult<NetappPortDTO> getNetappPortDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<NetappPort> page = getNetappPortPage(searchParams, pageNumber, pageSize); // 将List<NetappPort>中的数据转换为List<NetappPortDTO>
		List<NetappPortDTO> dtos = BeanMapper.mapList(page.getContent(), NetappPortDTO.class);
		PaginationResult<NetappPortDTO> paginationResult = new PaginationResult<NetappPortDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}