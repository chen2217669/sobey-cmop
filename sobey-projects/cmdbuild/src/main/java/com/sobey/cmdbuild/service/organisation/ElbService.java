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
import com.sobey.cmdbuild.entity.Elb;
import com.sobey.cmdbuild.repository.ElbDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.ElbDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Elb的service类.
 */
@Service
@Transactional
public class ElbService extends BasicSevcie {
	@Autowired
	private ElbDao elbDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Elb
	 */
	public Elb findElb(Integer id) {
		return elbDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param elb
	 * @return Elb
	 */
	public Elb saveOrUpdate(Elb elb) {
		return elbDao.save(elb);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteElb(Integer id) {
		elbDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return Elb
	 */
	public Elb findByCode(String code) {
		return elbDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<Elb>
	 */
	public List<Elb> getCompanies() {
		return elbDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Elb>
	 */
	private Page<Elb> getElbPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Elb> spec = buildSpecification(searchParams);
		return elbDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Elb>
	 */
	private Specification<Elb> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Elb> spec = DynamicSpecifications.bySearchFilter(filters.values(), Elb.class);
		return spec;
	}

	/**
	 * ElbDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<ElbDTO>
	 */
	public PaginationResult<ElbDTO> getElbDTOPagination(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<Elb> page = getElbPage(searchParams, pageNumber, pageSize); // 将List<Elb>中的数据转换为List<ElbDTO>
		List<ElbDTO> dtos = BeanMapper.mapList(page.getContent(), ElbDTO.class);
		PaginationResult<ElbDTO> paginationResult = new PaginationResult<ElbDTO>(page.getNumber(), page.getSize(),
				page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(), page.hasPreviousPage(),
				page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}