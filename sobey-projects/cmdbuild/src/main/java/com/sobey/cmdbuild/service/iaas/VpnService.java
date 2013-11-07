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
import com.sobey.cmdbuild.entity.Vpn;
import com.sobey.cmdbuild.repository.VpnDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.VpnDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Vpn的service类.
 */
@Service
@Transactional
public class VpnService extends BasicSevcie {
	@Autowired
	private VpnDao vpnDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Vpn
	 */
	public Vpn findVpn(Integer id) {
		return vpnDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param vpn
	 * @return Vpn
	 */
	public Vpn saveOrUpdate(Vpn vpn) {
		return vpnDao.save(vpn);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteVpn(Integer id) {
		vpnDao.delete(id);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Vpn>
	 */
	private Page<Vpn> getVpnPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Vpn> spec = buildSpecification(searchParams);
		return vpnDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Vpn>
	 */
	private Specification<Vpn> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Vpn> spec = DynamicSpecifications.bySearchFilter(filters.values(), Vpn.class);
		return spec;
	}

	/**
	 * VpnDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<VpnDTO>
	 */
	public PaginationResult<VpnDTO> getVpnDTOPagination(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<Vpn> page = getVpnPage(searchParams, pageNumber, pageSize); // 将List<Vpn>中的数据转换为List<VpnDTO>
		List<VpnDTO> dtos = BeanMapper.mapList(page.getContent(), VpnDTO.class);
		PaginationResult<VpnDTO> paginationResult = new PaginationResult<VpnDTO>(page.getNumber(), page.getSize(),
				page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(), page.hasPreviousPage(),
				page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}