package com.sobey.cmdbuild.service.infrastructure;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.NetappBox;
import com.sobey.cmdbuild.repository.NetappBoxDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.NetappBoxDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * NetappBox的service类.
 */
@Service
@Transactional
public class NetappBoxService extends BasicSevcie {
	@Autowired
	private NetappBoxDao netappBoxDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return NetappBox
	 */
	public NetappBox findNetappBox(Integer id) {
		return netappBoxDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param netappBox
	 * @return NetappBox
	 */
	public NetappBox saveOrUpdate(NetappBox netappBox) {
		return netappBoxDao.save(netappBox);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteNetappBox(Integer id) {
		netappBoxDao.delete(id);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<NetappBox>
	 */
	private Page<NetappBox> getNetappBoxPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<NetappBox> spec = buildSpecification(searchParams);
		return netappBoxDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<NetappBox>
	 */
	private Specification<NetappBox> buildSpecification(Map<String, Object> searchParams) { // 将条件查询放入Map中.查询条件可查询SearchFilter类.
		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<NetappBox> spec = DynamicSpecifications.bySearchFilter(filters.values(), NetappBox.class);
		return spec;
	}

	/**
	 * NetappBoxDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象. * @param searchParams 查询语句Map.
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<NetappBoxDTO>
	 */
	public PaginationResult<NetappBoxDTO> getNetappBoxDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {
		Page<NetappBox> page = getNetappBoxPage(searchParams, pageNumber, pageSize); // 将List<NetappBox>中的数据转换为List<NetappBoxDTO>
		List<NetappBoxDTO> dtos = BeanMapper.mapList(page.getContent(), NetappBoxDTO.class);
		PaginationResult<NetappBoxDTO> paginationResult = new PaginationResult<NetappBoxDTO>(page.getNumber(),
				page.getSize(), page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(),
				page.hasPreviousPage(), page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);
		return paginationResult;
	}
}