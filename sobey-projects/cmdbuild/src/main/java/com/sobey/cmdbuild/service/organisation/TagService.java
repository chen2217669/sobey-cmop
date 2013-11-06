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
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.repository.TagDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.TagDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Tag的service类.
 */
@Service
@Transactional
public class TagService extends BasicSevcie {

	@Autowired
	private TagDao tagDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Tag
	 */
	public Tag findTag(Integer id) {
		return tagDao.findOne(id);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param tag
	 * @return Tag
	 */
	public Tag saveOrUpdate(Tag tag) {
		return tagDao.save(tag);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteTag(Integer id) {
		tagDao.delete(id);
	}

	/**
	 * 根据code获得状态为"A"的有效对象
	 * 
	 * @param code
	 * @return Tag
	 */
	public Tag findByCode(String code) {
		return tagDao.findByCodeAndStatus(code, CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得所有对象集合
	 * 
	 * @return List<Tag>
	 */
	public List<Tag> getTags() {
		return tagDao.findAllByStatus(CMDBuildConstants.STATUS_ACTIVE);
	}

	/**
	 * 获得指定Tenants下所有对象集合
	 * 
	 * @param tenantsId
	 *            Tenants Id
	 * @return List<Tag>
	 */
	public List<Tag> getTagsByTenants(Integer tenantsId) {
		return tagDao.findAllByStatusAndTenants(CMDBuildConstants.STATUS_ACTIVE, tenantsId);
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Tag>
	 */
	private Page<Tag> getTagPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<Tag> spec = buildSpecification(searchParams);

		return tagDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.
	 * 
	 * @param searchParams
	 * @return Specification<Tag>
	 */
	private Specification<Tag> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), Tag.class);
	}

	/**
	 * TagDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<TagDTO>
	 */
	public PaginationResult<TagDTO> getTagDTOPagination(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		Page<Tag> page = getTagPage(searchParams, pageNumber, pageSize);

		List<TagDTO> dtos = BeanMapper.mapList(page.getContent(), TagDTO.class);

		return fillPaginationResult(page, dtos);
	}
}