package com.sobey.cmdbuild.webservice.impl;

import java.util.List;
import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.Validate;
import org.apache.cxf.feature.Features;
import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.ERROR;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.TagSoapService;
import com.sobey.cmdbuild.webservice.response.dto.TagDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "TagService", endpointInterface = "com.sobey.cmdbuild.webservice.TagSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class TagSoapServiceImpl extends BasicSoapSevcie implements TagSoapService {
	@Override
	public DTOResult<TagDTO> findTag(@WebParam(name = "id") Integer id) {
		DTOResult<TagDTO> result = new DTOResult<TagDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Tag tag = comm.tagService.findTag(id);
			Validate.notNull(tag, ERROR.OBJECT_NULL);
			TagDTO tagDTO = BeanMapper.map(tag, TagDTO.class);
			result.setDto(tagDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TagDTO> findTagByCode(@WebParam(name = "code") String code) {
		DTOResult<TagDTO> result = new DTOResult<TagDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Tag tag = comm.tagService.findByCode(code);
			Validate.notNull(tag, ERROR.OBJECT_NULL);
			TagDTO tagDTO = BeanMapper.map(tag, TagDTO.class);
			result.setDto(tagDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createTag(@WebParam(name = "tagDTO") TagDTO tagDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(tagDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.tagService.findByCode(tagDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Tag tag = BeanMapper.map(tagDTO, Tag.class);
			BeanValidators.validateWithException(validator, tag);
			comm.tagService.saveOrUpdate(tag);
			return new IdResult(tag.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateTag(@WebParam(name = "id") Integer id, @WebParam(name = "tagDTO") TagDTO tagDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(tagDTO, ERROR.INPUT_NULL);
			Tag tag = comm.tagService.findTag(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.tagService.findByCode(tagDTO.getCode()) == null || tag.getCode().equals(tagDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Tag tagEntity = BeanMapper.map(tagDTO, Tag.class);
			BeanMapper.copy(tagEntity, tag);
			tagEntity.setIdClass(Tag.class.getSimpleName());
			tagEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.tagService.saveOrUpdate(tagEntity);
			return new IdResult(tag.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteTag(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Tag tag = comm.tagService.findTag(id);
			Tag tagEntity = BeanMapper.map(findTag(id).getDto(), Tag.class);
			BeanMapper.copy(tagEntity, tag);
			tagEntity.setIdClass(Tag.class.getSimpleName());
			tagEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.tagService.saveOrUpdate(tagEntity);
			return new IdResult(tag.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<TagDTO> getTagPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<TagDTO> result = new PaginationResult<TagDTO>();
		try {
			result = comm.tagService.getTagDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<TagDTO> getTagList() {
		DTOListResult<TagDTO> result = new DTOListResult<TagDTO>();
		try {
			List<Tag> companies = comm.tagService.getCompanies();
			List<TagDTO> list = BeanMapper.mapList(companies, TagDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}