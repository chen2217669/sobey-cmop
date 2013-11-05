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
import com.sobey.cmdbuild.entity.Dns;
import com.sobey.cmdbuild.repository.DnsDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.DnsDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Dns的service类.
 */
@Service
@Transactional
public class DnsService extends BasicSevcie {
	@Autowired
	private DnsDao dnsDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Dns
	 */
	public Dns findDns(Integer id) {
		return dnsDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param dns
	 * @return Dns
	 */
	public Dns saveOrUpdate(Dns dns) {
		return dnsDao.save(dns);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteDns(Integer id) {
		dnsDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return Dns
	 */
	public Dns findByCode(String code) {
		return dnsDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<Dns>
	 */
	public List<Dns> getCompanies() {
		return dnsDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Dns>
	 */
	private Page<Dns> getDnsPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Dns> spec = buildSpecification(searchParams);
		return dnsDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Dns>
	 */
	private Specification<Dns> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Dns> spec = DynamicSpecifications.bySearchFilter(filters.values(), Dns.class);
		return spec;
	}

	/**
	 * DnsDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<DnsDTO>
	 */
	public PaginationResult<DnsDTO> getDnsDTOPagination(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<Dns> page = getDnsPage(searchParams, pageNumber, pageSize); // 将List<Dns>中的数据转换为List<DnsDTO>
		List<DnsDTO> dtos = BeanMapper.mapList(page.getContent(), DnsDTO.class);
		PaginationResult<DnsDTO> paginationResult = new PaginationResult<DnsDTO>(page.getNumber(), page.getSize(),
				page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(), page.hasPreviousPage(),
				page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}