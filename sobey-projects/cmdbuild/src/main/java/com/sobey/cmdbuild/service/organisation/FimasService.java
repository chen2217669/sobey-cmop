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
import com.sobey.cmdbuild.entity.Fimas;
import com.sobey.cmdbuild.repository.FimasDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.FimasDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Fimas的service类.
 */
@Service
@Transactional
public class FimasService extends BasicSevcie {
	@Autowired
	private FimasDao fimasDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Fimas
	 */
	public Fimas findFimas(Integer id) {
		return fimasDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param fimas
	 * @return Fimas
	 */
	public Fimas saveOrUpdate(Fimas fimas) {
		return fimasDao.save(fimas);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteFimas(Integer id) {
		fimasDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return Fimas
	 */
	public Fimas findByCode(String code) {
		return fimasDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<Fimas>
	 */
	public List<Fimas> getCompanies() {
		return fimasDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Fimas>
	 */
	private Page<Fimas> getFimasPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Fimas> spec = buildSpecification(searchParams);
		return fimasDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Fimas>
	 */
	private Specification<Fimas> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Fimas> spec = DynamicSpecifications.bySearchFilter(filters.values(), Fimas.class);
		return spec;
	}

	/**
	 * FimasDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如果每页为10行
	 * @return PaginationResult<FimasDTO>
	 */
	public PaginationResult<FimasDTO> getFimasDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<Fimas> page = getFimasPage(searchParams, pageNumber, pageSize); // 将List<Fimas>中的数据转换为List<FimasDTO>
		List<FimasDTO> dtos = BeanMapper.mapList(page.getContent(), FimasDTO.class);
		PaginationResult<FimasDTO> paginationResult = new PaginationResult<FimasDTO>(page.getNumber(), page.getSize(),
				page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(), page.hasPreviousPage(),
				page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}