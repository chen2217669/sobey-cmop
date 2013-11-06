package com.sobey.cmdbuild.service.financial;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.Consumptions;
import com.sobey.cmdbuild.repository.ConsumptionsDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.ConsumptionsDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Consumptions的service类.
 */
@Service
@Transactional
public class ConsumptionsService extends BasicSevcie {
	@Autowired
	private ConsumptionsDao consumptionsDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Consumptions
	 */
	public Consumptions findConsumptions(Integer id) {
		return consumptionsDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param consumptions
	 * @return Consumptions
	 */
	public Consumptions saveOrUpdate(Consumptions consumptions) {
		return consumptionsDao.save(consumptions);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteConsumptions(Integer id) {
		consumptionsDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return Consumptions
	 */
	public Consumptions findByCode(String code) {
		return consumptionsDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<Consumptions>
	 */
	public List<Consumptions> getCompanies() {
		return consumptionsDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Consumptions>
	 */
	private Page<Consumptions> getConsumptionsPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Consumptions> spec = buildSpecification(searchParams);
		return consumptionsDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Consumptions>
	 */
	private Specification<Consumptions> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Consumptions> spec = DynamicSpecifications.bySearchFilter(filters.values(), Consumptions.class);
		return spec;
	}

	/**
	 * ConsumptionsDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<ConsumptionsDTO>
	 */
	public PaginationResult<ConsumptionsDTO> getConsumptionsDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		Page<Consumptions> page = getConsumptionsPage(searchParams, pageNumber, pageSize); // 将List<Consumptions>中的数据转换为List<ConsumptionsDTO>
		List<ConsumptionsDTO> dtos = BeanMapper.mapList(page.getContent(), ConsumptionsDTO.class);
		PaginationResult<ConsumptionsDTO> paginationResult = new PaginationResult<ConsumptionsDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}